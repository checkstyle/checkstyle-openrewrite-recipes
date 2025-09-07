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

| Status | Meaning                                                                      |
|--------|------------------------------------------------------------------------------|
| 🟢     | **Full Coverage** – Complete auto-fix capability for all violation scenarios |
| 🟡     | **Partial Coverage** – Auto-fix available for some violation scenarios       |
| 🔴     | **Won't Be Covered** – Auto-fix not feasible or not planned                  |



### Annotations

| Status | Check                                                                                                                        | Recipe           | Coverage Notes |
|--------|------------------------------------------------------------------------------------------------------------------------------|------------------|----------------|
| 🟢     | [`AnnotationLocation`](https://checkstyle.sourceforge.io/checks/annotation/annotationlocation.html#AnnotationLocation)       | `TBD`            |                |
| 🟢     | [`AnnotationOnSameLine`](https://checkstyle.sourceforge.io/checks/annotation/annotationonsameline.html#AnnotationOnSameLine) | `TBD`            |                |
| 🔴     | [`AnnotationUseStyle`](https://checkstyle.sourceforge.io/checks/annotation/annotationusestyle.html#AnnotationUseStyle)       | `TBD`            | Standardize annotation syntax |
| 🟢     | [`MissingDeprecated`](https://checkstyle.sourceforge.io/checks/annotation/missingdeprecated.html#MissingDeprecated)         | `TBD`            |                |
| 🟢     | [`MissingOverride`](https://checkstyle.sourceforge.io/checks/annotation/missingoverride.html#MissingOverride)               | `TBD`            |                |
| 🟢     | [`PackageAnnotation`](https://checkstyle.sourceforge.io/checks/annotation/packageannotation.html#PackageAnnotation)         | `TBD`            |                |
| 🔴     | [`SuppressWarnings`](https://checkstyle.sourceforge.io/checks/annotation/suppresswarnings.html#SuppressWarnings)           | `TBD`            | Remove inappropriate suppressions |

### Block Checks

| Status | Check                                                                                                                        | Recipe           | Coverage Notes |
|--------|------------------------------------------------------------------------------------------------------------------------------|------------------|----------------|
| 🔴     | [`AvoidNestedBlocks`](https://checkstyle.sourceforge.io/checks/blocks/avoidnestedblocks.html#AvoidNestedBlocks)             | `TBD`            | Requires code restructuring |
| 🟢     | [`EmptyBlock`](https://checkstyle.sourceforge.io/checks/blocks/emptyblock.html#EmptyBlock)                                  | `TBD`            |                |
| 🟢     | [`EmptyCatchBlock`](https://checkstyle.sourceforge.io/checks/blocks/emptycatchblock.html#EmptyCatchBlock)                   | `TBD`            |                |
| 🟢     | [`LeftCurly`](https://checkstyle.sourceforge.io/checks/blocks/leftcurly.html#LeftCurly)                                     | `TBD`            |                |
| 🟢     | [`NeedBraces`](https://checkstyle.sourceforge.io/checks/blocks/needbraces.html#NeedBraces)                                  | `TBD`            |                |
| 🟢     | [`RightCurly`](https://checkstyle.sourceforge.io/checks/blocks/rightcurly.html#RightCurly)                                  | `TBD`            |                |


### Class Design

| Status | Check                                                                                                                        | Recipe           | Coverage Notes |
|--------|------------------------------------------------------------------------------------------------------------------------------|------------------|----------------|
| 🔴     | [`DesignForExtension`](https://checkstyle.sourceforge.io/checks/design/designforextension.html#DesignForExtension)         | `TBD`            | Requires design decisions (final/abstract) |
| 🟢     | [`FinalClass`](https://checkstyle.sourceforge.io/checks/design/finalclass.html#FinalClass)                                 | `TBD`            |                |
| 🟢     | [`HideUtilityClassConstructor`](https://checkstyle.sourceforge.io/checks/design/hideutilityclassconstructor.html#HideUtilityClassConstructor) | `TBD`            |                |
| 🟢     | [`InnerTypeLast`](https://checkstyle.sourceforge.io/checks/design/innertypelast.html#InnerTypeLast)                        | `TBD`            |                |
| 🔴     | [`InterfaceIsType`](https://checkstyle.sourceforge.io/checks/design/interfaceistype.html#InterfaceIsType)                  | `TBD`            | Remove non-type interface members |
| 🔴     | [`MutableException`](https://checkstyle.sourceforge.io/checks/design/mutableexception.html#MutableException)               | `TBD`            | Make exception fields final |
| 🔴     | [`OneTopLevelClass`](https://checkstyle.sourceforge.io/checks/design/onetoplevelclass.html#OneTopLevelClass)               | `TBD`            | Split into separate files |
| 🟢     | [`SealedShouldHavePermitsList`](https://checkstyle.sourceforge.io/checks/design/sealedshoulddhavepermitslist.html#SealedShouldHavePermitsList) | `TBD`            |                |
| 🔴     | [`ThrowsCount`](https://checkstyle.sourceforge.io/checks/design/throwscount.html#ThrowsCount)                              | `TBD`            | Reduce throws declarations |
| 🔴     | [`VisibilityModifier`](https://checkstyle.sourceforge.io/checks/design/visibilitymodifier.html#VisibilityModifier)         | `TBD`            | Change visibility modifiers |


### Coding

| Status | Check                                                                                                                        | Recipe           | Coverage Notes |
|--------|------------------------------------------------------------------------------------------------------------------------------|------------------|----------------|
| 🟢     | [`ArrayTrailingComma`](https://checkstyle.sourceforge.io/checks/coding/arraytrailingcomma.html#ArrayTrailingComma)         | `TBD`            |                |
| 🔴     | [`AvoidDoubleBraceInitialization`](https://checkstyle.sourceforge.io/checks/coding/avoiddoublebraceinitialization.html#AvoidDoubleBraceInitialization) | `TBD`            | Requires refactoring initialization logic |
| 🔴     | [`AvoidInlineConditionals`](https://checkstyle.sourceforge.io/checks/coding/avoidinlineconditionals.html#AvoidInlineConditionals) | `TBD`            | Requires extracting to if-else statements |
| 🟢     | [`AvoidNoArgumentSuperConstructorCall`](https://checkstyle.sourceforge.io/checks/coding/avoidnoargumentsuperconstructorcall.html#AvoidNoArgumentSuperConstructorCall) | `TBD`            |                |
| 🟢     | [`ConstructorsDeclarationGrouping`](https://checkstyle.sourceforge.io/checks/coding/constructorsdeclarationgrouping.html#ConstructorsDeclarationGrouping) | `TBD`            |                |
| 🔴     | [`CovariantEquals`](https://checkstyle.sourceforge.io/checks/coding/covariantequals.html#CovariantEquals)                   | `TBD`            | Requires implementing proper equals(Object) |
| 🟢     | [`DeclarationOrder`](https://checkstyle.sourceforge.io/checks/coding/declarationorder.html#DeclarationOrder)               | `TBD`            |                |
| 🟢     | [`DefaultComesLast`](https://checkstyle.sourceforge.io/checks/coding/defaultcomeslast.html#DefaultComesLast)               | `TBD`            |                |
| 🟢     | [`EmptyStatement`](https://checkstyle.sourceforge.io/checks/coding/emptystatement.html#EmptyStatement)                     | `TBD`            |                |
| 🟢     | [`EqualsAvoidNull`](https://checkstyle.sourceforge.io/checks/coding/equalsavoidnull.html#EqualsAvoidNull)                 | `TBD`            |                |
| 🔴     | [`EqualsHashCode`](https://checkstyle.sourceforge.io/checks/coding/equalshashcode.html#EqualsHashCode)                     | `TBD`            | Implement proper equals/hashCode pair |
| 🟢     | [`ExplicitInitialization`](https://checkstyle.sourceforge.io/checks/coding/explicitinitialization.html#ExplicitInitialization) | `TBD`            |                |
| 🔴     | [`FallThrough`](https://checkstyle.sourceforge.io/checks/coding/fallthrough.html#FallThrough)                             | `TBD`            | Add break statements or intentional comments |
| 🟢     | [`FinalLocalVariable`](https://checkstyle.sourceforge.io/checks/coding/finallocalvariable.html#FinalLocalVariable)         | `TBD`            |                |
| 🔴     | [`HiddenField`](https://checkstyle.sourceforge.io/checks/coding/hiddenfield.html#HiddenField)                             | `TBD`            | Rename variables or use this. prefix |
| 🔴     | [`IllegalCatch`](https://checkstyle.sourceforge.io/checks/coding/illegalcatch.html#IllegalCatch)                           | `TBD`            | Change catch block exception types |
| 🔴     | [`IllegalInstantiation`](https://checkstyle.sourceforge.io/checks/coding/illegalinstantiation.html#IllegalInstantiation)   | `TBD`            | Replace with factory methods |
| 🔴     | [`IllegalThrows`](https://checkstyle.sourceforge.io/checks/coding/illegalthrows.html#IllegalThrows)                       | `TBD`            | Change throws declarations |
| 🔴     | [`IllegalToken`](https://checkstyle.sourceforge.io/checks/coding/illegaltoken.html#IllegalToken)                         | `TBD`            | Replace illegal tokens |
| 🔴     | [`IllegalTokenText`](https://checkstyle.sourceforge.io/checks/coding/illegaltokentext.html#IllegalTokenText)               | `TBD`            | Change token text |
| 🔴     | [`IllegalType`](https://checkstyle.sourceforge.io/checks/coding/illegaltype.html#IllegalType)                             | `TBD`            | Replace with allowed types |
| 🔴     | [`InnerAssignment`](https://checkstyle.sourceforge.io/checks/coding/innerassignment.html#InnerAssignment)                 | `TBD`            | Extract assignments to separate statements |
| 🔴     | [`MagicNumber`](https://checkstyle.sourceforge.io/checks/coding/magicnumber.html#MagicNumber)                             | `TBD`            | Extract to named constants |
| 🔴     | [`MatchXpath`](https://checkstyle.sourceforge.io/checks/coding/matchxpath.html#MatchXpath)                               | `TBD`            | Context-dependent XPath violations |
| 🔴     | [`MissingCtor`](https://checkstyle.sourceforge.io/checks/coding/missingctor.html#MissingCtor)                             | `TBD`            | Add explicit constructor |
| 🟢     | [`MissingNullCaseInSwitch`](https://checkstyle.sourceforge.io/checks/coding/missingnullcaseinswitch.html#MissingNullCaseInSwitch) | `TBD`            |                |
| 🟢     | [`MissingSwitchDefault`](https://checkstyle.sourceforge.io/checks/coding/missingswitchdefault.html#MissingSwitchDefault)   | `TBD`            |                |
| 🔴     | [`ModifiedControlVariable`](https://checkstyle.sourceforge.io/checks/coding/modifiedcontrolvariable.html#ModifiedControlVariable) | `TBD`            | Restructure loop logic |
| 🔴     | [`MultipleStringLiterals`](https://checkstyle.sourceforge.io/checks/coding/multiplestringliterals.html#MultipleStringLiterals) | `TBD`            | Extract to constants |
| 🟢     | [`MultipleVariableDeclarations`](https://checkstyle.sourceforge.io/checks/coding/multiplevariabledeclarations.html#MultipleVariableDeclarations) | `TBD`            |                |
| 🔴     | [`NestedForDepth`](https://checkstyle.sourceforge.io/checks/coding/nestedfordepth.html#NestedForDepth)                   | `TBD`            | Requires loop restructuring |
| 🔴     | [`NestedIfDepth`](https://checkstyle.sourceforge.io/checks/coding/nestedifdepth.html#NestedIfDepth)                       | `TBD`            | Requires conditional restructuring |
| 🔴     | [`NestedTryDepth`](https://checkstyle.sourceforge.io/checks/coding/nestedtrydepth.html#NestedTryDepth)                   | `TBD`            | Requires exception handling restructuring |
| 🟢     | [`NoArrayTrailingComma`](https://checkstyle.sourceforge.io/checks/coding/noarraytrailingcomma.html#NoArrayTrailingComma) | `TBD`            |                |
| 🔴     | [`NoClone`](https://checkstyle.sourceforge.io/checks/coding/noclone.html#NoClone)                                       | `TBD`            | Remove clone() method |
| 🟢     | [`NoEnumTrailingComma`](https://checkstyle.sourceforge.io/checks/coding/noenumtrailingcomma.html#NoEnumTrailingComma)     | `TBD`            |                |
| 🔴     | [`NoFinalizer`](https://checkstyle.sourceforge.io/checks/coding/nofinalizer.html#NoFinalizer)


### Headers

| Status | Check                                                                           | Recipe                                                                                                                                      | Coverage Notes             |
|--------|---------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------|----------------------------|
| 🟡     | [`Header`](https://checkstyle.sourceforge.io/checks/header/header.html#Header ) | [`Header`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/Header.java ) | only java files are fixed. |



### Imports


| Status | Check                                                                                                       | Recipe                                                                                                                                                       | Coverage Notes |
|--------|-------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------|
| 🟢     | [`RedundantImport`](https://checkstyle.sourceforge.io/checks/imports/redundantimport.html#RedundantImport ) | [`RedundantImport`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/RedundantImport.java) |                |



### Javadoc Comments

_No checks analyzed yet_


### Metrics

_No checks analyzed yet_


### Miscellaneous

| Status | Check                                                                               | Recipe                                                                                                                                          | Coverage Notes |
|--------|-------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------|----------------|
| 🟢     | [`UpperEll`](https://checkstyle.sourceforge.io/checks/misc/upperell.html#UpperEll ) | [`UpperEll`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/UpperEll.java ) |                |


### Modifiers

_No checks analyzed yet_


### Naming Conventions

| Status | Check                                                                                                                                | Recipe  | Coverage Notes                                                                       |
|--------|--------------------------------------------------------------------------------------------------------------------------------------|---------|--------------------------------------------------------------------------------------|
| 🔴     | [`AbbreviationAsWordInName`](https://checkstyle.sourceforge.io/checks/naming/abbreviationaswordinname.html#AbbreviationAsWordInName) |         | Requires semantic understanding of abbreviations and context                         |
| 🟡     | [`AbstractClassName`](https://checkstyle.sourceforge.io/checks/naming/abstractclassname.html#AbstractClassName)                      | `TBD`   | Partially covered by renaming abstract class names to match the configured pattern.  | 


### Regexp

_No checks analyzed yet_


### Size Violations

_No checks analyzed yet_


### Whitespace

_No checks analyzed yet_

