# checkstyle-openrewrite-recipes
This OpenRewrite recipe automatically fixes Checkstyle violations in your Java project by analyzing the Checkstyle report and applying code transformations to resolve common issues.

## Prerequisites
You need a Java project that already has the Checkstyle plugin configured and running.

## Setup
First, add the OpenRewrite plugin and our autofix recipe dependency to your build configuration.

### Example:
```xml
<plugin>
  <groupId>org.openrewrite.maven</groupId>
  <artifactId>rewrite-maven-plugin</artifactId>
  <version>${rewrite.maven.plugin}</version>
  <configuration>
    <activeRecipes>
      <recipe>CheckstyleAutoFix</recipe>
    </activeRecipes>
  </configuration>
  <dependencies>
    <dependency>
      <groupId>com.puppycrawl.tools</groupId>
      <artifactId>checkstyle-openrewrite-recipes</artifactId>
      <version>1.0.0</version>
    </dependency>
  </dependencies>
</plugin>
```
## Configuration
Create a `rewrite.yml` file in your project root:

```yml
---
type: specs.openrewrite.org/v1beta/recipe
name: CheckstyleAutoFix
displayName: Checkstyle Auto Fix
description: Automatically fix Checkstyle violations
recipeList:
  - org.checkstyle.autofix.CheckstyleAutoFix:
      violationReportPath: "target/checkstyle/checkstyle-report.xml"
      configurationPath: "config/checkstyle.xml"
      propertiesPath: "config/checkstyle.properties"
```

Parameters:
- `violationReportPath`: Path to Checkstyle XML report (required)
- `configurationPath`: Path to Checkstyle configuration file (required)
- `propertiesPath`: Path to Checkstyle properties file (optional)

## How to use it
The autofix process works in two steps: first generate a Checkstyle report, then run the autofix recipe.
```
mvn checkstyle:check    # Generate the violation report
mvn rewrite:run         # Apply the fixes
```
## OpenRewrite Recipe Coverage for Checkstyle Checks

This table tracks the auto-fix support status of OpenRewrite recipes for each Checkstyle check. Organized by Checkstyle categories, it helps contributors identify which checks are:

- Fully supported via auto-fix
- Partially supported
- Not feasible to auto-fix

### Status Legend

| Status | Meaning |
|--------|---------|
| 🔵 | **Implemented** – Recipes have been created for these checks |
| 🟡 | **Partial Coverage** – Recipes exist but only cover specific violation scenarios |
| 🟢 | **Planned** – Feasible to implement, but no recipe exists yet |
| 🔴 | **Not Supported** – Cannot be implemented or not feasible to auto-fix |
| ⚪ | **Pending Review** – Automatically detected requires human review to determine feasibility |

### Annotations

| Status | Check                                                                                                                        | Coverage Notes |
|--------|------------------------------------------------------------------------------------------------------------------------------|----------------|
| 🔵     | [`AnnotationLocation`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/AnnotationLocation.java)       |                |
| 🔵     | [`AnnotationOnSameLine`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/AnnotationOnSameLine.java) |                |
| 🔴     | [`AnnotationUseStyle`](https://checkstyle.sourceforge.io/checks/annotation/annotationusestyle.html#AnnotationUseStyle)       | Standardize annotation syntax |
| 🔵     | [`MissingDeprecated`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/MissingDeprecated.java)         |                |
| 🔵     | [`MissingOverride`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/MissingOverride.java)               |                |
| 🔵     | [`MissingOverrideOnRecordAccessor`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/MissingOverrideOnRecordAccessor.java) |                |
| 🔵     | [`OpenjdkAnnotationLocation`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/OpenjdkAnnotationLocation.java)         |                |
| 🔵     | [`PackageAnnotation`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/PackageAnnotation.java)         |                |
| 🔴     | [`SuppressWarnings`](https://checkstyle.sourceforge.io/checks/annotation/suppresswarnings.html#SuppressWarnings)           | Remove inappropriate suppressions |
| 🔴     | [`SuppressWarningsHolder`](https://checkstyle.sourceforge.io/checks/annotation/suppresswarningsholder.html#SuppressWarningsHolder) | Infrastructural module for SuppressWarningsFilter |

### Block Checks

| Status | Check                                                                                                                        | Coverage Notes |
|--------|------------------------------------------------------------------------------------------------------------------------------|----------------|
| 🔴     | [`AvoidNestedBlocks`](https://checkstyle.sourceforge.io/checks/blocks/avoidnestedblocks.html#AvoidNestedBlocks)             | Requires code restructuring |
| 🔵     | [`EmptyBlock`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/EmptyBlock.java)                                  |                |
| 🔵     | [`EmptyCatchBlock`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/EmptyCatchBlock.java)                   |                |
| 🔵     | [`LeftCurly`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/LeftCurly.java)                                     |                |
| 🔵     | [`NeedBraces`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/NeedBraces.java)                                  |                |
| 🔵     | [`RightCurly`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/RightCurly.java)                                  |                |


### Class Design

| Status | Check                                                                                                                        | Coverage Notes |
|--------|------------------------------------------------------------------------------------------------------------------------------|----------------|
| 🔴     | [`DesignForExtension`](https://checkstyle.sourceforge.io/checks/design/designforextension.html#DesignForExtension)         | Requires design decisions (final/abstract) |
| 🔵     | [`FinalClass`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/FinalClass.java)                                 |                |
| 🔵     | [`HideUtilityClassConstructor`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/HideUtilityClassConstructor.java) |                |
| 🔵     | [`InnerTypeLast`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/InnerTypeLast.java)                        |                |
| 🔴     | [`InterfaceIsType`](https://checkstyle.sourceforge.io/checks/design/interfaceistype.html#InterfaceIsType)                  | Remove non-type interface members |
| 🔴     | [`MutableException`](https://checkstyle.sourceforge.io/checks/design/mutableexception.html#MutableException)               | Make exception fields final |
| 🔴     | [`OneTopLevelClass`](https://checkstyle.sourceforge.io/checks/design/onetoplevelclass.html#OneTopLevelClass)               | Split into separate files |
| 🔵     | [`SealedShouldHavePermitsList`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/SealedShouldHavePermitsList.java) |                |
| 🔴     | [`ThrowsCount`](https://checkstyle.sourceforge.io/checks/design/throwscount.html#ThrowsCount)                              | Reduce throws declarations |
| 🔴     | [`VisibilityModifier`](https://checkstyle.sourceforge.io/checks/design/visibilitymodifier.html#VisibilityModifier)         | Change visibility modifiers |


### Coding

| Status | Check                                                                                                                        | Coverage Notes |
|--------|------------------------------------------------------------------------------------------------------------------------------|----------------|
| 🔵     | [`ArrayTrailingComma`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/ArrayTrailingComma.java)         |                |
| 🔴     | [`AvoidDoubleBraceInitialization`](https://checkstyle.sourceforge.io/checks/coding/avoiddoublebraceinitialization.html#AvoidDoubleBraceInitialization) | Requires refactoring initialization logic |
| 🔴     | [`AvoidInlineConditionals`](https://checkstyle.sourceforge.io/checks/coding/avoidinlineconditionals.html#AvoidInlineConditionals) | Requires extracting to if-else statements |
| 🔵     | [`AvoidNoArgumentSuperConstructorCall`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/AvoidNoArgumentSuperConstructorCall.java) |                |
| 🔵     | [`ConstructorsDeclarationGrouping`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/ConstructorsDeclarationGrouping.java) |                |
| 🔴     | [`CovariantEquals`](https://checkstyle.sourceforge.io/checks/coding/covariantequals.html#CovariantEquals)                   | Requires implementing proper equals(Object) |
| 🔵     | [`DeclarationOrder`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/DeclarationOrder.java)               |                |
| 🔵     | [`DefaultComesLast`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/DefaultComesLast.java)               |                |
| 🔵     | [`EmptyStatement`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/EmptyStatement.java)                     |                |
| 🔵     | [`EqualsAvoidNull`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/EqualsAvoidNull.java)                 |                |
| 🔴     | [`EqualsHashCode`](https://checkstyle.sourceforge.io/checks/coding/equalshashcode.html#EqualsHashCode)                     | Implement proper equals/hashCode pair |
| 🔵     | [`ExplicitInitialization`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/ExplicitInitialization.java) |                |
| 🔴     | [`FallThrough`](https://checkstyle.sourceforge.io/checks/coding/fallthrough.html#FallThrough)                             | Add break statements or intentional comments |
| 🔵     | [`FinalLocalVariable`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/FinalLocalVariable.java)         |                |
| 🔴     | [`HiddenField`](https://checkstyle.sourceforge.io/checks/coding/hiddenfield.html#HiddenField)                             | Rename variables or use this. prefix |
| 🔴     | [`IllegalCatch`](https://checkstyle.sourceforge.io/checks/coding/illegalcatch.html#IllegalCatch)                           | Change catch block exception types |
| 🔴     | [`IllegalInstantiation`](https://checkstyle.sourceforge.io/checks/coding/illegalinstantiation.html#IllegalInstantiation)   | Replace with factory methods |
| 🔴     | [`IllegalSymbol`](https://checkstyle.sourceforge.io/checks/coding/illegalsymbol.html#IllegalSymbol) |                |
| 🔴     | [`IllegalThrows`](https://checkstyle.sourceforge.io/checks/coding/illegalthrows.html#IllegalThrows)                       | Change throws declarations |
| 🔴     | [`IllegalToken`](https://checkstyle.sourceforge.io/checks/coding/illegaltoken.html#IllegalToken)                         | Replace illegal tokens |
| 🔴     | [`IllegalTokenText`](https://checkstyle.sourceforge.io/checks/coding/illegaltokentext.html#IllegalTokenText)               | Change token text |
| 🔴     | [`IllegalType`](https://checkstyle.sourceforge.io/checks/coding/illegaltype.html#IllegalType)                             | Replace with allowed types |
| 🔴     | [`InnerAssignment`](https://checkstyle.sourceforge.io/checks/coding/innerassignment.html#InnerAssignment)                 | Extract assignments to separate statements |
| 🔴     | [`MagicNumber`](https://checkstyle.sourceforge.io/checks/coding/magicnumber.html#MagicNumber)                             | Extract to named constants |
| 🔴     | [`MatchXpath`](https://checkstyle.sourceforge.io/checks/coding/matchxpath.html#MatchXpath)                               | Context-dependent XPath violations |
| 🔴     | [`MissingCtor`](https://checkstyle.sourceforge.io/checks/coding/missingctor.html#MissingCtor)                             | Add explicit constructor |
| 🔵     | [`MissingNullCaseInSwitch`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/MissingNullCaseInSwitch.java) |                |
| 🔵     | [`MissingSwitchDefault`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/MissingSwitchDefault.java)   |                |
| 🔴     | [`ModifiedControlVariable`](https://checkstyle.sourceforge.io/checks/coding/modifiedcontrolvariable.html#ModifiedControlVariable) | Restructure loop logic |
| 🔴     | [`MultipleStringLiterals`](https://checkstyle.sourceforge.io/checks/coding/multiplestringliterals.html#MultipleStringLiterals) | Extract to constants |
| 🔵     | [`MultipleVariableDeclarations`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/MultipleVariableDeclarations.java) |                |
| 🔴     | [`NestedForDepth`](https://checkstyle.sourceforge.io/checks/coding/nestedfordepth.html#NestedForDepth)                   | Requires loop restructuring |
| 🔴     | [`NestedIfDepth`](https://checkstyle.sourceforge.io/checks/coding/nestedifdepth.html#NestedIfDepth)                       | Requires conditional restructuring |
| 🔴     | [`NestedTryDepth`](https://checkstyle.sourceforge.io/checks/coding/nestedtrydepth.html#NestedTryDepth)                   | Requires exception handling restructuring |
| 🔵     | [`NoArrayTrailingComma`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/NoArrayTrailingComma.java) |                |
| 🔴     | [`NoClone`](https://checkstyle.sourceforge.io/checks/coding/noclone.html#NoClone)                                       | Remove clone() method |
| 🔵     | [`NoEnumTrailingComma`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/NoEnumTrailingComma.java)     |                |
| 🔴     | [`NoFinalizer`](https://checkstyle.sourceforge.io/checks/coding/nofinalizer.html#NoFinalizer)                             | Remove finalize() method |
| 🔵     | [`OneStatementPerLine`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/OneStatementPerLine.java) |                |
| 🔵     | [`OverloadMethodsDeclarationOrder`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/OverloadMethodsDeclarationOrder.java) |                |
| 🔵     | [`PackageDeclaration`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/PackageDeclaration.java) |                |
| 🔴     | [`ParameterAssignment`](https://checkstyle.sourceforge.io/checks/coding/parameterassignment.html#ParameterAssignment) |                |
| 🔴     | [`PatternVariableAssignment`](https://checkstyle.sourceforge.io/checks/coding/patternvariableassignment.html#PatternVariableAssignment) |                |
| 🔵     | [`RequireThis`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/RequireThis.java) |                |
| 🔴     | [`ReturnCount`](https://checkstyle.sourceforge.io/checks/coding/returncount.html#ReturnCount) |                |
| 🔵     | [`SimplifyBooleanExpression`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/SimplifyBooleanExpression.java) |                |
| 🔵     | [`SimplifyBooleanReturn`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/SimplifyBooleanReturn.java) |                |
| 🔵     | [`StringLiteralEquality`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/StringLiteralEquality.java) |                |
| 🔴     | [`SuperClone`](https://checkstyle.sourceforge.io/checks/coding/superclone.html#SuperClone) |                |
| 🔵     | [`SuperFinalize`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/SuperFinalize.java) |                |
| 🔵     | [`TextBlockGoogleStyleFormatting`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/TextBlockGoogleStyleFormatting.java) |                |
| 🔵     | [`UnnecessaryTypeArgumentsWithRecordPattern`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/UnnecessaryTypeArgumentsWithRecordPattern.java) |                |
| 🔵     | [`UnnecessaryNullCheckWithInstanceOf`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/UnnecessaryNullCheckWithInstanceOf.java) |                |
| 🔵     | [`UnnecessaryParentheses`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/UnnecessaryParentheses.java) |                |
| 🔵     | [`UnnecessarySemicolonAfterOuterTypeDeclaration`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/UnnecessarySemicolonAfterOuterTypeDeclaration.java) |                |
| 🔵     | [`UnnecessarySemicolonAfterTypeMemberDeclaration`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/UnnecessarySemicolonAfterTypeMemberDeclaration.java) |                |
| 🔵     | [`UnnecessarySemicolonInEnumeration`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/UnnecessarySemicolonInEnumeration.java) |                |
| 🔵     | [`UnnecessarySemicolonInTryWithResources`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/UnnecessarySemicolonInTryWithResources.java) |                |
| 🔵     | [`UnusedCatchParameterShouldBeUnnamed`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/UnusedCatchParameterShouldBeUnnamed.java) |                |
| 🔵     | [`UnusedLambdaParameterShouldBeUnnamed`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/UnusedLambdaParameterShouldBeUnnamed.java) |                |
| 🔵     | [`UnusedLocalVariable`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/UnusedLocalVariable.java)   |                |
| 🔵     | [`UnusedTryResourceShouldBeUnnamed`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/UnusedTryResourceShouldBeUnnamed.java) |                |
| 🔵     | [`UseEnhancedSwitch`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/UseEnhancedSwitch.java)           |                |
| 🔴     | [`VariableDeclarationUsageDistance`](https://checkstyle.sourceforge.io/checks/coding/variabledeclarationusagedistance.html#VariableDeclarationUsageDistance) |                |
| 🔴     | [`WhenShouldBeUsed`](https://checkstyle.sourceforge.io/checks/coding/whenshouldbeused.html#WhenShouldBeUsed) |                |

### Headers

| Status | Check                                                                           | Coverage Notes             |
|--------|---------------------------------------------------------------------------------|----------------------------|
| 🟡     | [`Header`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/Header.java) | only java files are fixed. |
| 🔴     | [`MultiFileRegexpHeader`](https://checkstyle.sourceforge.io/checks/header/multifileregexpheader.html#MultiFileRegexpHeader) | Fix header content |
| 🔴     | [`RegexpHeader`](https://checkstyle.sourceforge.io/checks/header/regexpheader.html#RegexpHeader)                           | Fix header content |


### Imports

| Status | Check                                                                                                                        | Coverage Notes |
|--------|------------------------------------------------------------------------------------------------------------------------------|----------------|
| 🔵     | [`AvoidStarImport`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/AvoidStarImport.java)                 |                |
| 🔵     | [`AvoidStaticImport`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/AvoidStaticImport.java)           |                |
| 🔵     | [`CustomImportOrder`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/CustomImportOrder.java)           |                |
| 🔴     | [`IllegalImport`](https://checkstyle.sourceforge.io/checks/imports/illegalimport.html#IllegalImport)                       | Replace with allowed imports |
| 🔴     | [`ImportControl`](https://checkstyle.sourceforge.io/checks/imports/importcontrol.html#ImportControl)                       | Restructure imports per rules |
| 🔵     | [`ImportOrder`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/ImportOrder.java)                             |                |
| 🔵     | [`RedundantImport`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/RedundantImport.java)                 |                |
| 🔵     | [`UnusedImports`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/UnusedImports.java)                       |                |


### Javadoc Comments

| Status | Check                                                                                                                        | Coverage Notes |
|--------|------------------------------------------------------------------------------------------------------------------------------|----------------|
| 🔵     | [`AtclauseOrder`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/AtclauseOrder.java)                       |                |
| 🔵     | [`IllegalBlockTag`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/IllegalBlockTag.java)                 |                |
| 🔵     | [`InvalidJavadocPosition`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/InvalidJavadocPosition.java) |                |
| 🔵     | [`JavadocBlockTagLocation`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/JavadocBlockTagLocation.java) |                |
| 🔵     | [`JavadocContentLocation`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/JavadocContentLocation.java) |                |
| 🔵     | [`JavadocLeadingAsteriskAlign`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/JavadocLeadingAsteriskAlign.java) |                |
| 🔴     | [`JavadocMethod`](https://checkstyle.sourceforge.io/checks/javadoc/javadocmethod.html#JavadocMethod)                       | Add/fix method documentation |
| 🔵     | [`JavadocMissingLeadingAsterisk`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/JavadocMissingLeadingAsterisk.java) |                |
| 🔵     | [`JavadocMissingWhitespaceAfterAsterisk`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/JavadocMissingWhitespaceAfterAsterisk.java) |                |
| 🔴     | [`JavadocPackage`](https://checkstyle.sourceforge.io/checks/javadoc/javadocpackage.html#JavadocPackage)                   | Create package-info.java |
| 🔵     | [`JavadocParagraph`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/JavadocParagraph.java)             |                |
| 🔴     | [`JavadocRegexp`](https://checkstyle.sourceforge.io/checks/javadoc/javadocregexp.html#JavadocRegexp)                       | Context-dependent pattern matching |
| 🔵     | [`JavadocTagContinuationIndentation`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/JavadocTagContinuationIndentation.java) |                |
| 🔴     | [`JavadocType`](https://checkstyle.sourceforge.io/checks/javadoc/javadoctype.html#JavadocType)                           | Add/fix type documentation |
| 🔴     | [`JavadocVariable`](https://checkstyle.sourceforge.io/checks/javadoc/javadocvariable.html#JavadocVariable)                 | Add variable documentation |
| 🔴     | [`MissingJavadocMethod`](https://checkstyle.sourceforge.io/checks/javadoc/missingjavadocmethod.html#MissingJavadocMethod)   | Add method documentation |
| 🔴     | [`MissingJavadocPackage`](https://checkstyle.sourceforge.io/checks/javadoc/missingjavadocpackage.html#MissingJavadocPackage) | Add package documentation |
| 🔴     | [`MissingJavadocType`](https://checkstyle.sourceforge.io/checks/javadoc/missingjavadoctype.html#MissingJavadocType)         | Add type documentation |
| 🔴     | [`NonEmptyAtclauseDescription`](https://checkstyle.sourceforge.io/checks/javadoc/nonemptyatclausedescription.html#NonEmptyAtclauseDescription) | Add tag descriptions |
| 🔵     | [`PreferLiteralJavadocInlineTag`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/PreferLiteralJavadocInlineTag.java) |                |
| 🔵     | [`RequireEmptyLineBeforeBlockTagGroup`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/RequireEmptyLineBeforeBlockTagGroup.java) |                |
| 🔵     | [`SingleLineJavadoc`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/SingleLineJavadoc.java)           |                |
| 🔴     | [`SummaryJavadoc`](https://checkstyle.sourceforge.io/checks/javadoc/summaryjavadoc.html#SummaryJavadoc)                   | Rewrite summary sentences |
| 🔴     | [`WriteTag`](https://checkstyle.sourceforge.io/checks/javadoc/writetag.html#WriteTag)                                     | Add/fix custom Javadoc tags |


### Metrics

| Status | Check                                                                                                                        | Coverage Notes |
|--------|------------------------------------------------------------------------------------------------------------------------------|----------------|
| 🔴     | [`BooleanExpressionComplexity`](https://checkstyle.sourceforge.io/checks/metrics/booleanexpressioncomplexity.html#BooleanExpressionComplexity) | Requires breaking down complex expressions |
| 🔴     | [`ClassDataAbstractionCoupling`](https://checkstyle.sourceforge.io/checks/metrics/classdataabstractioncoupling.html#ClassDataAbstractionCoupling) | Requires architectural changes |
| 🔴     | [`ClassFanOutComplexity`](https://checkstyle.sourceforge.io/checks/metrics/classfanoutcomplexity.html#ClassFanOutComplexity) | Requires architectural refactoring |
| 🔴     | [`CyclomaticComplexity`](https://checkstyle.sourceforge.io/checks/metrics/cyclomaticcomplexity.html#CyclomaticComplexity)   | Requires method decomposition |
| 🔴     | [`JavaNCSS`](https://checkstyle.sourceforge.io/checks/metrics/javancss.html#JavaNCSS)                                     | Requires code simplification |
| 🔴     | [`NPathComplexity`](https://checkstyle.sourceforge.io/checks/metrics/npathcomplexity.html#NPathComplexity)                 | Requires method decomposition |

### Miscellaneous

| Status | Check                                                                                                          | Coverage Notes |
|--------|----------------------------------------------------------------------------------------------------------------|----------------|
| 🔵     | [`ArrayTypeStyle`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/ArrayTypeStyle.java)         |                |
| 🔴     | [`AvoidEscapedUnicodeCharacters`](https://checkstyle.sourceforge.io/checks/misc/avoidescapedunicodecharacters.html#AvoidEscapedUnicodeCharacters) | Need to determine appropriate replacements |
| 🔵     | [`CommentsIndentation`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/CommentsIndentation.java) |                |
| 🔵     | [`MultilineCommentLeadingAsteriskPresence`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/MultilineCommentLeadingAsteriskPresence.java) | |
| 🔴     | [`DescendantToken`](https://checkstyle.sourceforge.io/checks/misc/descendanttoken.html#DescendantToken)       | Context-dependent token restrictions |
| 🔵     | [`FinalParameters`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/FinalParameters.java)       |                |
| 🔵     | [`HexLiteralCase`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/HexLiteralCase.java)                                       |                |
| 🔵     | [`Indentation`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/Indentation.java)                 |                |
| 🔵     | [`LineEnding`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/LineEnding.java) |                |
| 🔵     | [`NewlineAtEndOfFile`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/NewlineAtEndOfFile.java) |                |
| 🔴     | [`NoCodeInFile`](https://checkstyle.sourceforge.io/checks/misc/nocodeinfile.html#NoCodeInFile)             | Add code or remove file |
| 🔵     | [`NumericalPrefixesInfixesSuffixesCharacterCase`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/NumericalPrefixesInfixesSuffixesCharacterCase.java)                         |                |
| 🔴     | [`OrderedProperties`](https://checkstyle.sourceforge.io/checks/misc/orderedproperties.html#OrderedProperties) | Reorder properties |
| 🔴     | [`OuterTypeFilename`](https://checkstyle.sourceforge.io/checks/misc/outertypefilename.html#OuterTypeFilename) | Rename file or class |
| 🔴     | [`TodoComment`](https://checkstyle.sourceforge.io/checks/misc/todocomment.html#TodoComment)                 | Resolve TODO comments |
| 🔵     | [`TrailingComment`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/TrailingComment.java)       |                |
| 🔴     | [`Translation`](https://checkstyle.sourceforge.io/checks/misc/translation.html#Translation)                 | Fix property file translations |
| 🔵     | [`UncommentedMain`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/UncommentedMain.java)     |                |
| 🔴     | [`UniqueProperties`](https://checkstyle.sourceforge.io/checks/misc/uniqueproperties.html#UniqueProperties)   | Remove duplicate properties |
| 🔵     | [`UpperEll`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/UpperEll.java)                         |                |

### Modifiers

| Status | Check                                                                                                                        | Coverage Notes |
|--------|------------------------------------------------------------------------------------------------------------------------------|----------------|
| 🔵     | [`AnnotatedDeclarationVisibility`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/AnnotatedDeclarationVisibility.java)       |                |
| 🔵     | [`ClassMemberImpliedModifier`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/ClassMemberImpliedModifier.java) |                |
| 🔵     | [`InterfaceMemberImpliedModifier`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/InterfaceMemberImpliedModifier.java) |                |
| 🔵     | [`ModifierOrder`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/ModifierOrder.java)                       |                |
| 🔵     | [`RedundantModifier`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/RedundantModifier.java)           |                |

### Naming Conventions

| Status | Check                                                                                                                        | Coverage Notes |
|--------|------------------------------------------------------------------------------------------------------------------------------|----------------|
| 🔴     | [`AbbreviationAsWordInName`](https://checkstyle.sourceforge.io/checks/naming/abbreviationaswordinname.html#AbbreviationAsWordInName) | Requires semantic understanding of abbreviations and context |
| 🟡     | [`AbstractClassName`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/AbstractClassName.java)             | Partially covered by renaming abstract class names to match the configured pattern. |
| 🔵     | [`CatchParameterName`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/CatchParameterName.java)           |                |
| 🔵     | [`ClassTypeParameterName`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/ClassTypeParameterName.java) |                |
| 🔵     | [`ConstantName`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/ConstantName.java)                           |                |
| 🔴     | [`GoogleMethodName`](https://checkstyle.sourceforge.io/checks/naming/googlemethodname.html#GoogleMethodName)               | Requires manual renaming |
| 🔴     | [`GoogleNonConstantFieldName`](https://checkstyle.sourceforge.io/checks/naming/googlenonconstantfieldname.html#GoogleNonConstantFieldName) |                |
| 🔴     | [`IllegalIdentifierName`](https://checkstyle.sourceforge.io/checks/naming/illegalidentifiername.html#IllegalIdentifierName) | Rename identifiers |
| 🔵     | [`InterfaceTypeParameterName`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/InterfaceTypeParameterName.java) |                |
| 🔵     | [`LambdaParameterName`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/LambdaParameterName.java)       |                |
| 🔵     | [`LocalFinalVariableName`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/LocalFinalVariableName.java) |                |
| 🔵     | [`LocalVariableName`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/LocalVariableName.java)             |                |
| 🔵     | [`MemberName`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/MemberName.java)                                 |                |
| 🔵     | [`MethodName`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/MethodName.java)                                 |                |
| 🔵     | [`MethodTypeParameterName`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/MethodTypeParameterName.java) |                |
| 🔴     | [`PackageName`](https://checkstyle.sourceforge.io/checks/naming/packagename.html#PackageName)                             | Rename package |
| 🔵     | [`ParameterName`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/ParameterName.java)                       |                |
| 🔵     | [`PatternVariableName`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/PatternVariableName.java)       |                |
| 🔵     | [`RecordComponentName`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/RecordComponentName.java)       |                |
| 🔵     | [`RecordTypeParameterName`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/RecordTypeParameterName.java) |                |
| 🔵     | [`StaticVariableName`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/StaticVariableName.java)         |                |
| 🔵     | [`TypeName`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/TypeName.java)                                       |                |

### Regexp

| Status | Check                                                                                                                        | Coverage Notes |
|--------|------------------------------------------------------------------------------------------------------------------------------|----------------|
| 🔴     | [`Regexp`](https://checkstyle.sourceforge.io/checks/regexp/regexp.html#Regexp)                                             | Context-dependent pattern matching |
| 🔴     | [`RegexpMultiline`](https://checkstyle.sourceforge.io/checks/regexp/regexpmultiline.html#RegexpMultiline)                 | Context-dependent pattern fixes |
| 🔴     | [`RegexpOnFilename`](https://checkstyle.sourceforge.io/checks/regexp/regexponfilename.html#RegexpOnFilename)               | Rename files |
| 🔴     | [`RegexpSingleline`](https://checkstyle.sourceforge.io/checks/regexp/regexpsingleline.html#RegexpSingleline)             | Context-dependent line fixes |
| 🔴     | [`RegexpSinglelineJava`](https://checkstyle.sourceforge.io/checks/regexp/regexpsinglelinejava.html#RegexpSinglelineJava) | Context-dependent Java line fixes |

### Size Violations

| Status | Check                                                                                                                        | Coverage Notes |
|--------|------------------------------------------------------------------------------------------------------------------------------|----------------|
| 🔴     | [`AnonInnerLength`](https://checkstyle.sourceforge.io/checks/sizes/anoninnerlength.html#AnonInnerLength)                   | Requires refactoring to named classes |
| 🔴     | [`ExecutableStatementCount`](https://checkstyle.sourceforge.io/checks/sizes/executablestatementcount.html#ExecutableStatementCount) | Requires method decomposition |
| 🔴     | [`FileLength`](https://checkstyle.sourceforge.io/checks/sizes/filelength.html#FileLength)                                 | Requires file splitting |
| 🔴     | [`LambdaBodyLength`](https://checkstyle.sourceforge.io/checks/sizes/lambdabodylength.html#LambdaBodyLength)               | Extract lambda to method |
| 🔴     | [`LineLength`](https://checkstyle.sourceforge.io/checks/sizes/linelength.html#LineLength)                                 | Requires line breaking decisions |
| 🔴     | [`MethodCount`](https://checkstyle.sourceforge.io/checks/sizes/methodcount.html#MethodCount)                             | Requires class decomposition |
| 🔴     | [`MethodLength`](https://checkstyle.sourceforge.io/checks/sizes/methodlength.html#MethodLength)                           | Requires method decomposition |
| 🔴     | [`OuterTypeNumber`](https://checkstyle.sourceforge.io/checks/sizes/outertypenumber.html#OuterTypeNumber)                 | Split types into separate files |
| 🔴     | [`ParameterNumber`](https://checkstyle.sourceforge.io/checks/sizes/parameternumber.html#ParameterNumber)                 | Reduce parameter count |
| 🔴     | [`RecordComponentNumber`](https://checkstyle.sourceforge.io/checks/sizes/recordcomponentnumber.html#RecordComponentNumber) | Reduce record components |

### Whitespace

| Status | Check                                                                                                                        | Coverage Notes |
|--------|------------------------------------------------------------------------------------------------------------------------------|----------------|
| 🔵     | [`ArrayBracketNoWhitespace`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/ArrayBracketNoWhitespace.java) |                |
| 🔵     | [`EmptyForInitializerPad`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/EmptyForInitializerPad.java) |                |
| 🔵     | [`EmptyForIteratorPad`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/EmptyForIteratorPad.java) |                |
| 🔵     | [`EmptyLineSeparator`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/EmptyLineSeparator.java)     |                |
| 🔵     | [`FileTabCharacter`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/FileTabCharacter.java)           |                |
| 🔵     | [`GenericWhitespace`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/GenericWhitespace.java)       |                |
| 🔵     | [`MethodParamPad`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/MethodParamPad.java)                 |                |
| 🔴     | [`NoLineWrap`](https://checkstyle.sourceforge.io/checks/whitespace/nolinewrap.html#NoLineWrap)                           | Requires line unwrapping decisions |
| 🔵     | [`NoWhitespaceAfter`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/NoWhitespaceAfter.java)       |                |
| 🔵     | [`NoWhitespaceBefore`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/NoWhitespaceBefore.java)         |                |
| 🔵     | [`NoWhitespaceBeforeCaseDefaultColon`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/NoWhitespaceBeforeCaseDefaultColon.java) |                |
| 🔵     | [`OperatorWrap`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/OperatorWrap.java)                     |                |
| 🔵     | [`ParenPad`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/ParenPad.java)                                 |                |
| 🔵     | [`SeparatorWrap`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/SeparatorWrap.java)                   |                |
| 🔵     | [`SingleSpaceSeparator`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/SingleSpaceSeparator.java) |                |
| 🔵     | [`TypeBodyPadding`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/TypeBodyPadding.java)                 |                |
| 🔵     | [`TypecastParenPad`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/TypecastParenPad.java)         |                |
| 🔵     | [`WhitespaceAfter`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/WhitespaceAfter.java)             |                |
| 🔵     | [`WhitespaceAround`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/WhitespaceAround.java)           |                |
| 🔵     | [`WhitespaceBeforeEmptyBody`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/WhitespaceBeforeEmptyBody.java)                 |                |
