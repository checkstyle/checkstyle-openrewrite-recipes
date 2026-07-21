#!/bin/bash
set -e

RECIPES_DIR="$(cd "$(dirname "$0")/.." && pwd)"
WORK_DIR="${RECIPES_DIR}/.ci-temp/diff-report-kafka"
TARGET_REPO="https://github.com/apache/kafka.git"
TARGET_SHA="fdc59be8c84af1424b2f51a2df81386927f01a90"

function getMavenProperty {
  property="\${$1}"
  (cd "$RECIPES_DIR" && ./mvnw -e --no-transfer-progress -q -Dexec.executable='echo' \
                      -Dexec.args="${property}" \
                      --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
}

function generateGradleInitScript {
  sed -e "s|@REWRITE_GRADLE_PLUGIN_VERSION@|$REWRITE_GRADLE_PLUGIN_VERSION|g" \
      -e "s|@WORK_DIR@|$WORK_DIR|g" \
      -e "s|@RECIPES_VERSION@|$RECIPES_VERSION|g" \
      "$RECIPES_DIR/.ci/kafka-init.gradle.template" > "$WORK_DIR/init.gradle"
}

CHECKSTYLE_VERSION=$(getMavenProperty checkstyle.version)
REWRITE_GRADLE_PLUGIN_VERSION="latest.release"
RECIPES_VERSION=$(getMavenProperty project.version)
BASE_BRANCH="${GITHUB_BASE_REF:-main}"
PR_COMMIT=$(git rev-parse HEAD)

mkdir -p "$WORK_DIR"
cp "$RECIPES_DIR/.ci/checkstyle-config-with-all-autofixed-checks-kafka.xml" \
  "$WORK_DIR/checkstyle-config.xml"
cp "$RECIPES_DIR/.ci/diff-report-rewrite.yml" "$WORK_DIR/diff-report-rewrite.yml"
cp "$RECIPES_DIR/.ci/kafka-header.txt" "$WORK_DIR/kafka-header.txt"

sed -i.bak "s|@WORK_DIR@|$WORK_DIR|g" "$WORK_DIR/checkstyle-config.xml" \
  && rm -f "$WORK_DIR/checkstyle-config.xml.bak"

echo "Fetching base branch $BASE_BRANCH..."
git fetch origin "$BASE_BRANCH"

echo "Checking out baseline recipes..."
git checkout -f "origin/$BASE_BRANCH"

echo "Building and installing baseline checkstyle-openrewrite-recipes locally..."
cd "$RECIPES_DIR"
./mvnw clean install -DskipTests -Dcheckstyle.skip=true -Drewrite.skip=true \
  --no-transfer-progress

echo "Cloning target repo..."
if [ ! -d "$WORK_DIR/kafka" ]; then
    mkdir -p "$WORK_DIR/kafka"
    cd "$WORK_DIR/kafka"
    git init
    git remote add origin "$TARGET_REPO"
    git fetch --depth 1 origin "$TARGET_SHA"
    git checkout FETCH_HEAD
    cd "$RECIPES_DIR"
else
    echo "Target repo already cloned."
    cd "$WORK_DIR/kafka"
    git fetch origin "$TARGET_SHA"
    git checkout -f "$TARGET_SHA"
    git reset --hard "$TARGET_SHA"
    git clean -fdx
    cd "$RECIPES_DIR"
fi

echo "Downloading Checkstyle CLI..."
CHECKSTYLE_JAR="$WORK_DIR/checkstyle-${CHECKSTYLE_VERSION}-all.jar"
if [ ! -f "$CHECKSTYLE_JAR" ]; then
    DL_URL="https://github.com/checkstyle/checkstyle/releases/download"
    DL_URL="${DL_URL}/checkstyle-${CHECKSTYLE_VERSION}"
    DL_URL="${DL_URL}/checkstyle-${CHECKSTYLE_VERSION}-all.jar"
    curl -L -o "$CHECKSTYLE_JAR" "$DL_URL"
fi

echo "Executing Checkstyle CLI to generate baseline violation report..."
java -jar "$CHECKSTYLE_JAR" \
  -c "$WORK_DIR/checkstyle-config.xml" \
  -f xml \
  -o "$WORK_DIR/checkstyle-report.xml" \
  "$WORK_DIR/kafka" \
  || true

echo "Verifying that each Checkstyle module produced at least one violation..."

# until https://github.com/checkstyle/checkstyle-openrewrite-recipes/issues/330
SUPPRESSED_MODULES="AvoidStarImport|EmptyStatement|RedundantImport|UpperEll|UnusedImports"
MODULES=$(grep -o '<module name="[^"]*"' "$WORK_DIR/checkstyle-config.xml" \
  | sed 's/<module name="//' | sed 's/"//' \
  | grep -vE '^(Checker|TreeWalker|BeforeExecutionExclusionFileFilter)$' \
  | grep -vE "^($SUPPRESSED_MODULES)$")

MISSING_VIOLATIONS=0
for MODULE in $MODULES; do
    if ! grep -q "source=\".*\.${MODULE}Check\"" "$WORK_DIR/checkstyle-report.xml" && \
       ! grep -q "source=\".*\.${MODULE}\"" "$WORK_DIR/checkstyle-report.xml"; then
        echo "ERROR: No violations found for Checkstyle module: $MODULE"
        MISSING_VIOLATIONS=1
    fi
done

if [ "$MISSING_VIOLATIONS" = "1" ]; then
    echo "ERROR: Some Checkstyle modules did not produce any violations in the Kafka project!"
    echo "Please configure the check with non-default properties or find a different SHA to ensure violations."
    exit 1
fi

echo "Generating OpenRewrite configuration..."
cp "$WORK_DIR/diff-report-rewrite.yml" "$WORK_DIR/resolved-diff-report-rewrite.yml"
sed -i.bak "s|@WORK_DIR@|$WORK_DIR|g" "$WORK_DIR/resolved-diff-report-rewrite.yml" \
  && rm -f "$WORK_DIR/resolved-diff-report-rewrite.yml.bak"

echo "Generating Gradle init script for OpenRewrite..."
generateGradleInitScript

echo "Running OpenRewrite on the target project with BASELINE recipes..."
cd "$WORK_DIR/kafka"
./gradlew --init-script ../init.gradle rewriteRun --no-daemon --no-parallel -Dorg.gradle.jvmargs="-Xmx8g"

echo "Saving baseline changes..."
git config user.email "ci@example.com" || true
git config user.name "CI" || true
git add .
git commit -m "Baseline changes" || true
BASELINE_COMMIT=$(git rev-parse HEAD)

echo "Resetting to original target state..."
if [ "$BASELINE_COMMIT" != "$(git rev-parse HEAD~1 2>/dev/null || git rev-parse HEAD)" ]; then
    git reset --hard HEAD~1
    git clean -fdx
fi

echo "Switching back to PR branch..."
cd "$RECIPES_DIR"
git checkout -f "$PR_COMMIT"

echo "Building and installing PR checkstyle-openrewrite-recipes locally..."
./mvnw clean install -DskipTests -Dcheckstyle.skip=true -Drewrite.skip=true \
  --no-transfer-progress

echo "Generating Gradle init script for OpenRewrite (PR)..."
generateGradleInitScript

echo "Running OpenRewrite on the target project with PR recipes..."
cd "$WORK_DIR/kafka"
./gradlew --init-script ../init.gradle rewriteRun --no-daemon --no-parallel -Dorg.gradle.jvmargs="-Xmx8g"

echo "Verifying compilability after PR recipe execution..."
COMPILE_FAILED=0
./gradlew compileJava compileTestJava --no-daemon 2>&1 | tee "$WORK_DIR/compile.log" || COMPILE_FAILED=1

if [ "$COMPILE_FAILED" = "1" ]; then
    echo "WARNING: Recipe produced non-compilable code!"
    echo "Compilation errors saved to: $WORK_DIR/compile.log"
fi

echo "Generating Delta Diff report..."
git checkout config/rewrite.yml || true
git diff "$BASELINE_COMMIT" > "$WORK_DIR/recipe-diff.patch.txt"

if [ -s "$WORK_DIR/recipe-diff.patch.txt" ]; then
  echo "Diff report generated: $WORK_DIR/recipe-diff.patch.txt"
  echo "$(wc -l < "$WORK_DIR/recipe-diff.patch.txt") lines of changes"

  echo "Generating HTML diff report..."
  npx -y diff2html-cli -s side -i file -F "$WORK_DIR/recipe-diff.html" \
    -- "$WORK_DIR/recipe-diff.patch.txt"
  echo "HTML diff report generated: $WORK_DIR/recipe-diff.html"

  echo "Generating line-by-line HTML diff report..."
  npx -y diff2html-cli -s line -i file -F "$WORK_DIR/recipe-diff-line-by-line.html" \
    -- "$WORK_DIR/recipe-diff.patch.txt"
  echo "Line-by-line HTML diff report generated: $WORK_DIR/recipe-diff-line-by-line.html"
else
  echo "No changes produced by recipes compared to baseline"
  echo "<html><body><h2>No changes produced by recipes compared to baseline.</h2></body></html>" \
    > "$WORK_DIR/recipe-diff.html"
  echo "Empty HTML diff report generated: $WORK_DIR/recipe-diff.html"
  echo "<html><body><h2>No changes produced by recipes compared to baseline.</h2></body></html>" \
    > "$WORK_DIR/recipe-diff-line-by-line.html"
  echo "Empty HTML diff report generated: $WORK_DIR/recipe-diff-line-by-line.html"
fi

if [ "$COMPILE_FAILED" = "1" ]; then
  exit 1
fi
