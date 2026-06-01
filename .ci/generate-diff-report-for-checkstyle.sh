#!/bin/bash
set -e

RECIPES_DIR="$(cd "$(dirname "$0")/.." && pwd)"
WORK_DIR="${RECIPES_DIR}/.ci-temp/diff-report"
TARGET_REPO="https://github.com/checkstyle/checkstyle.git"
TARGET_BRANCH="master"

function getMavenProperty {
  property="\${$1}"
  (cd "$RECIPES_DIR" && ./mvnw -e --no-transfer-progress -q -Dexec.executable='echo' \
                      -Dexec.args="${property}" \
                      --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec)
}

CHECKSTYLE_VERSION=$(getMavenProperty checkstyle.version)
REWRITE_PLUGIN_VERSION=$(getMavenProperty rewrite.maven.plugin)
RECIPES_VERSION=$(getMavenProperty project.version)
BASE_BRANCH="${GITHUB_BASE_REF:-main}"
PR_COMMIT=$(git rev-parse HEAD)

mkdir -p "$WORK_DIR"
cp "$RECIPES_DIR/.ci/checkstyle-config-with-all-autofixed-checks.xml" \
  "$WORK_DIR/checkstyle-config.xml"
cp "$RECIPES_DIR/.ci/rewrite-pom.xml" "$WORK_DIR/rewrite-pom.xml"
cp "$RECIPES_DIR/.ci/diff-report-rewrite.yml" "$WORK_DIR/diff-report-rewrite.yml"

echo "Fetching base branch $BASE_BRANCH..."
git fetch origin "$BASE_BRANCH"

echo "Checking out baseline recipes..."
git checkout "origin/$BASE_BRANCH"

echo "Building and installing baseline checkstyle-openrewrite-recipes locally..."
cd "$RECIPES_DIR"
./mvnw clean install -DskipTests -Dcheckstyle.skip=true -Drewrite.skip=true \
  --no-transfer-progress

echo "Cloning target repo..."
if [ ! -d "$WORK_DIR/checkstyle" ]; then
    git clone --depth 1 --branch "$TARGET_BRANCH" "$TARGET_REPO" "$WORK_DIR/checkstyle"
else
    echo "Target repo already cloned."
    cd "$WORK_DIR/checkstyle"
    git checkout "$TARGET_BRANCH"
    git reset --hard HEAD
    git clean -fd
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

echo "Executing Checkstyle CLI..."
java -jar "$CHECKSTYLE_JAR" \
  -c "$WORK_DIR/checkstyle-config.xml" \
  -f xml \
  -o "$WORK_DIR/checkstyle-report.xml" \
  "$WORK_DIR/checkstyle/src/main/java" \
  || true

echo "Generating OpenRewrite configuration..."
mkdir -p "$WORK_DIR/checkstyle/config"
mkdir -p "$WORK_DIR/checkstyle/.ci-temp"
cp "$WORK_DIR/diff-report-rewrite.yml" "$WORK_DIR/checkstyle/.ci-temp/diff-report-rewrite.yml"
sed -i.bak "s|@WORK_DIR@|$WORK_DIR|g" "$WORK_DIR/checkstyle/.ci-temp/diff-report-rewrite.yml" \
  && rm -f "$WORK_DIR/checkstyle/.ci-temp/diff-report-rewrite.yml.bak"

cd "$WORK_DIR/checkstyle"
CHECKSTYLE_VERSION=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout)

echo "Generating external POM for OpenRewrite..."
cp "$WORK_DIR/rewrite-pom.xml" ".ci-temp/rewrite-pom.xml"
sed -i.bak "s/@CHECKSTYLE_VERSION@/$CHECKSTYLE_VERSION/g" ".ci-temp/rewrite-pom.xml" \
  && rm -f ".ci-temp/rewrite-pom.xml.bak"

echo "Running OpenRewrite on the target project with BASELINE recipes..."
"$RECIPES_DIR/mvnw" --no-transfer-progress -U -f .ci-temp/rewrite-pom.xml \
  org.openrewrite.maven:rewrite-maven-plugin:run \
  -Drewrite.maven.plugin="$REWRITE_PLUGIN_VERSION" \
  -Dcheckstyle.recipes.version="$RECIPES_VERSION" \
  -Djacoco.skip=true \
  -DskipTests=true \
  -Dcheckstyle.skip=true \
  -Dpmd.skip=true \
  -Dxml.skip=true \
  -Dexec.skip=true

echo "Saving baseline changes..."
git config user.email "ci@example.com" || true
git config user.name "CI" || true
git add .
git commit -m "Baseline changes" || true
BASELINE_COMMIT=$(git rev-parse HEAD)

echo "Resetting to original target state..."
if [ "$BASELINE_COMMIT" != "$(git rev-parse HEAD~1 2>/dev/null || git rev-parse HEAD)" ]; then
    git reset --hard HEAD~1
fi

echo "Switching back to PR branch..."
cd "$RECIPES_DIR"
git checkout "$PR_COMMIT"

echo "Building and installing PR checkstyle-openrewrite-recipes locally..."
./mvnw clean install -DskipTests -Dcheckstyle.skip=true -Drewrite.skip=true \
  --no-transfer-progress

echo "Running OpenRewrite on the target project with PR recipes..."
cd "$WORK_DIR/checkstyle"
"$RECIPES_DIR/mvnw" --no-transfer-progress -U -f .ci-temp/rewrite-pom.xml \
  org.openrewrite.maven:rewrite-maven-plugin:run \
  -Drewrite.maven.plugin="$REWRITE_PLUGIN_VERSION" \
  -Dcheckstyle.recipes.version="$RECIPES_VERSION" \
  -Djacoco.skip=true \
  -DskipTests=true \
  -Dcheckstyle.skip=true \
  -Dpmd.skip=true \
  -Dxml.skip=true \
  -Dexec.skip=true

echo "Verifying compilability after PR recipe execution..."
COMPILE_FAILED=0
mvn compile --no-transfer-progress 2>&1 | tee "$WORK_DIR/compile.log" || COMPILE_FAILED=1

if [ "$COMPILE_FAILED" = "1" ]; then
    echo "WARNING: Recipe produced non-compilable code!"
    echo "Compilation errors saved to: $WORK_DIR/compile.log"
fi

echo "Generating Delta Diff report..."
git checkout config/rewrite.yml || true
git diff "$BASELINE_COMMIT" > "$WORK_DIR/recipe-diff.patch"

if [ -s "$WORK_DIR/recipe-diff.patch" ]; then
  echo "Diff report generated: $WORK_DIR/recipe-diff.patch"
  echo "$(wc -l < "$WORK_DIR/recipe-diff.patch") lines of changes"

  echo "Generating HTML diff report..."
  npx -y diff2html-cli -s side -i file -F "$WORK_DIR/recipe-diff.html" \
    -- "$WORK_DIR/recipe-diff.patch"
  echo "HTML diff report generated: $WORK_DIR/recipe-diff.html"

  echo "Generating line-by-line HTML diff report..."
  npx -y diff2html-cli -s line -i file -F "$WORK_DIR/recipe-diff-line-by-line.html" \
    -- "$WORK_DIR/recipe-diff.patch"
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
