#!/bin/bash
set -e

RECIPES_DIR="$(cd "$(dirname "$0")/.." && pwd)"
WORK_DIR="${RECIPES_DIR}/target/diff-report"
TARGET_REPO="https://github.com/checkstyle/checkstyle.git"
TARGET_BRANCH="master"
CHECKSTYLE_VERSION="13.5.0"

mkdir -p "$WORK_DIR"

echo "Building and installing checkstyle-openrewrite-recipes"
cd "$RECIPES_DIR"
./mvnw install -DskipTests -Dcheckstyle.skip=true -Drewrite.skip=true --no-transfer-progress

echo "Cloning target repo..."
if [ ! -d "$WORK_DIR/checkstyle" ]; then
    git clone --depth 1 --branch "$TARGET_BRANCH" "$TARGET_REPO" "$WORK_DIR/checkstyle"
else
    echo "Target repo already cloned."
fi

echo "Running Checkstyle on the target project..."
CHECKSTYLE_JAR="$WORK_DIR/checkstyle-${CHECKSTYLE_VERSION}-all.jar"
if [ ! -f "$CHECKSTYLE_JAR" ]; then
    curl -L -o "$CHECKSTYLE_JAR" "https://github.com/checkstyle/checkstyle/releases/download/checkstyle-${CHECKSTYLE_VERSION}/checkstyle-${CHECKSTYLE_VERSION}-all.jar"
fi

cat > "$WORK_DIR/checkstyle-config.xml" << 'EOF'
<?xml version="1.0"?>
<!DOCTYPE module PUBLIC
          "-//Checkstyle//DTD Checkstyle Configuration 1.3//EN"
          "https://checkstyle.org/dtds/configuration_1_3.dtd">
<module name="Checker">
    <property name="charset" value="UTF-8"/>
    <property name="fileExtensions" value="java, properties, xml"/>

    <module name="Header"/>
    <module name="NewlineAtEndOfFile"/>

    <module name="TreeWalker">
        <module name="AnnotationOnSameLine"/>
        <module name="AvoidStarImport"/>
        <module name="ConstructorsDeclarationGrouping"/>
        <module name="EmptyStatement"/>
        <module name="FinalClass"/>
        <module name="FinalLocalVariable"/>
        <module name="HexLiteralCase"/>
        <module name="MissingDeprecated"/>
        <module name="MissingOverride"/>
        <module name="NumericalPrefixesInfixesSuffixesCharacterCase"/>
        <module name="RedundantImport"/>
        <module name="UnusedImports"/>
        <module name="UnusedLocalVariable"/>
        <module name="UpperEll"/>
        <module name="UseEnhancedSwitch"/>
    </module>
</module>
EOF

echo "Executing Checkstyle CLI..."
java -jar "$CHECKSTYLE_JAR" \
  -c "$WORK_DIR/checkstyle-config.xml" \
  -f xml \
  -o "$WORK_DIR/checkstyle-report.xml" \
  "$WORK_DIR/checkstyle/src/main/java" \
  || true

echo "Generating OpenRewrite configuration..."
cat > "$WORK_DIR/checkstyle/config/rewrite.yml" << EOF
---
type: specs.openrewrite.org/v1beta/recipe
name: org.checkstyle.AllAutoFixes
displayName: Checkstyle Auto Fix For Diff Report
description: Auto-fix checkstyle violations for diff report generation.
recipeList:
  - org.checkstyle.autofix.CheckstyleAutoFix:
      violationReportPath: "${WORK_DIR}/checkstyle-report.xml"
      configurationPath: "${WORK_DIR}/checkstyle-config.xml"
EOF

echo "Running OpenRewrite on the target project..."
cd "$WORK_DIR/checkstyle"

CHECKSTYLE_VERSION=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout)

echo "Generating external POM for OpenRewrite..."
cat > "rewrite-pom.xml" << EOF
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>com.puppycrawl.tools</groupId>
  <artifactId>checkstyle-rewrite</artifactId>
  <version>${CHECKSTYLE_VERSION}</version>
  <packaging>pom</packaging>
  <build>
    <sourceDirectory>src/main/java</sourceDirectory>
    <testSourceDirectory>src/test/java</testSourceDirectory>
    <plugins>
      <plugin>
        <groupId>org.openrewrite.maven</groupId>
        <artifactId>rewrite-maven-plugin</artifactId>
        <version>6.17.0</version>
        <configuration>
          <configLocation>\${project.basedir}/config/rewrite.yml</configLocation>
          <activeRecipes>
            <recipe>org.checkstyle.AllAutoFixes</recipe>
          </activeRecipes>
          <exclusions>
            <exclusion>**.ci-temp**</exclusion>
            <exclusion>**resources-noncompilable**</exclusion>
            <exclusion>**src/it/resources**</exclusion>
            <exclusion>**src/test/resources**</exclusion>
            <exclusion>**xdocs-examples**</exclusion>
          </exclusions>
          <plainTextMasks>
            <plainTextMask>**/*.groovy</plainTextMask>
            <plainTextMask>**/*.xml</plainTextMask>
            <plainTextMask>**/*.yml</plainTextMask>
            <plainTextMask>**/resources-noncompilable/**</plainTextMask>
            <plainTextMask>**/resources/**</plainTextMask>
          </plainTextMasks>
        </configuration>
        <dependencies>
          <dependency>
            <groupId>com.puppycrawl.tools</groupId>
            <artifactId>checkstyle-openrewrite-recipes</artifactId>
            <version>1.0.0-SNAPSHOT</version>
          </dependency>
        </dependencies>
      </plugin>
    </plugins>
  </build>
</project>
EOF

mvn -f rewrite-pom.xml org.openrewrite.maven:rewrite-maven-plugin:6.17.0:run \
  -Djacoco.skip=true \
  -DskipTests=true \
  -Dcheckstyle.skip=true \
  -Dpmd.skip=true \
  -Dxml.skip=true \
  -Dexec.skip=true \
  --no-transfer-progress

echo "Verifying compilability after recipe execution..."
COMPILE_FAILED=0
mvn compile --no-transfer-progress 2>&1 | tee "$WORK_DIR/compile.log" || COMPILE_FAILED=1

if [ "$COMPILE_FAILED" = "1" ]; then
    echo "WARNING: Recipe produced non-compilable code!"
    echo "Compilation errors saved to: $WORK_DIR/compile.log"
fi

echo "Generating diff report..."
git checkout config/rewrite.yml || true
git diff > "$WORK_DIR/recipe-diff.patch"

if [ -s "$WORK_DIR/recipe-diff.patch" ]; then
  echo "Diff report generated: $WORK_DIR/recipe-diff.patch"
  echo "$(wc -l < "$WORK_DIR/recipe-diff.patch") lines of changes"

  echo "Generating HTML diff report..."
  npx -y diff2html-cli -s side -i file -F "$WORK_DIR/recipe-diff.html" -- "$WORK_DIR/recipe-diff.patch"
  echo "HTML diff report generated: $WORK_DIR/recipe-diff.html"
else
  echo "No changes produced by recipes"
fi

if [ "$COMPILE_FAILED" = "1" ]; then
  exit 1
fi
