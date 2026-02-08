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
| 🔴     | [`AnnotationUseStyle`](https://checkstyle.sourceforge.io/checks/annotation/annotationusestyle.html#AnnotationUseStyle)       |            | Standardize annotation syntax |
| 🟢     | [`MissingDeprecated`](https://checkstyle.sourceforge.io/checks/annotation/missingdeprecated.html#MissingDeprecated)         | `TBD`            |                |
| 🟢     | [`MissingOverride`](https://checkstyle.sourceforge.io/checks/annotation/missingoverride.html#MissingOverride)               | `TBD`            |                |
| 🟢     | [`PackageAnnotation`](https://checkstyle.sourceforge.io/checks/annotation/packageannotation.html#PackageAnnotation)         | `TBD`            |                |
| 🔴     | [`SuppressWarnings`](https://checkstyle.sourceforge.io/checks/annotation/suppresswarnings.html#SuppressWarnings)           |            | Remove inappropriate suppressions |

### Block Checks

| Status | Check                                                                                                                        | Recipe           | Coverage Notes |
|--------|------------------------------------------------------------------------------------------------------------------------------|------------------|----------------|
| 🔴     | [`AvoidNestedBlocks`](https://checkstyle.sourceforge.io/checks/blocks/avoidnestedblocks.html#AvoidNestedBlocks)             |            | Requires code restructuring |
| 🟢     | [`EmptyBlock`](https://checkstyle.sourceforge.io/checks/blocks/emptyblock.html#EmptyBlock)                                  | `TBD`            |                |
| 🟢     | [`EmptyCatchBlock`](https://checkstyle.sourceforge.io/checks/blocks/emptycatchblock.html#EmptyCatchBlock)                   | `TBD`            |                |
| 🟢     | [`LeftCurly`](https://checkstyle.sourceforge.io/checks/blocks/leftcurly.html#LeftCurly)                                     | `TBD`            |                |
| 🟢     | [`NeedBraces`](https://checkstyle.sourceforge.io/checks/blocks/needbraces.html#NeedBraces)                                  | `TBD`            |                |
| 🟢     | [`RightCurly`](https://checkstyle.sourceforge.io/checks/blocks/rightcurly.html#RightCurly)                                  | `TBD`            |                |


### Class Design

| Status | Check                                                                                                                        | Recipe           | Coverage Notes |
|--------|------------------------------------------------------------------------------------------------------------------------------|------------------|----------------|
| 🔴     | [`DesignForExtension`](https://checkstyle.sourceforge.io/checks/design/designforextension.html#DesignForExtension)         |            | Requires design decisions (final/abstract) |
| 🟢     | [`FinalClass`](https://checkstyle.sourceforge.io/checks/design/finalclass.html#FinalClass)                                 | [`FinalClass`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/FinalClass.java) |                |
| 🟢     | [`HideUtilityClassConstructor`](https://checkstyle.sourceforge.io/checks/design/hideutilityclassconstructor.html#HideUtilityClassConstructor) | `TBD`            |                |
| 🟢     | [`InnerTypeLast`](https://checkstyle.sourceforge.io/checks/design/innertypelast.html#InnerTypeLast)                        | `TBD`            |                |
| 🔴     | [`InterfaceIsType`](https://checkstyle.sourceforge.io/checks/design/interfaceistype.html#InterfaceIsType)                  |            | Remove non-type interface members |
| 🔴     | [`MutableException`](https://checkstyle.sourceforge.io/checks/design/mutableexception.html#MutableException)               |            | Make exception fields final |
| 🔴     | [`OneTopLevelClass`](https://checkstyle.sourceforge.io/checks/design/onetoplevelclass.html#OneTopLevelClass)               |            | Split into separate files |
| 🟢     | [`SealedShouldHavePermitsList`](https://checkstyle.sourceforge.io/checks/design/sealedshoulddhavepermitslist.html#SealedShouldHavePermitsList) | `TBD`            |                |
| 🔴     | [`ThrowsCount`](https://checkstyle.sourceforge.io/checks/design/throwscount.html#ThrowsCount)                              |            | Reduce throws declarations |
| 🔴     | [`VisibilityModifier`](https://checkstyle.sourceforge.io/checks/design/visibilitymodifier.html#VisibilityModifier)         |            | Change visibility modifiers |


### Coding

| Status | Check                                                                                                                        | Recipe          | Coverage Notes |
|--------|------------------------------------------------------------------------------------------------------------------------------|-----------------|----------------|
| 🟢     | [`ArrayTrailingComma`](https://checkstyle.sourceforge.io/checks/coding/arraytrailingcomma.html#ArrayTrailingComma)         | `TBD`           |                |
| 🔴     | [`AvoidDoubleBraceInitialization`](https://checkstyle.sourceforge.io/checks/coding/avoiddoublebraceinitialization.html#AvoidDoubleBraceInitialization) |           | Requires refactoring initialization logic |
| 🔴     | [`AvoidInlineConditionals`](https://checkstyle.sourceforge.io/checks/coding/avoidinlineconditionals.html#AvoidInlineConditionals) |           | Requires extracting to if-else statements |
| 🟢     | [`AvoidNoArgumentSuperConstructorCall`](https://checkstyle.sourceforge.io/checks/coding/avoidnoargumentsuperconstructorcall.html#AvoidNoArgumentSuperConstructorCall) | `TBD`           |                |
| 🟢     | [`ConstructorsDeclarationGrouping`](https://checkstyle.sourceforge.io/checks/coding/constructorsdeclarationgrouping.html#ConstructorsDeclarationGrouping) | `TBD`           |                |
| 🔴     | [`CovariantEquals`](https://checkstyle.sourceforge.io/checks/coding/covariantequals.html#CovariantEquals)                   |           | Requires implementing proper equals(Object) |
| 🟢     | [`DeclarationOrder`](https://checkstyle.sourceforge.io/checks/coding/declarationorder.html#DeclarationOrder)               | `TBD`           |                |
| 🟢     | [`DefaultComesLast`](https://checkstyle.sourceforge.io/checks/coding/defaultcomeslast.html#DefaultComesLast)               | `TBD`           |                |
| 🟢     | [`EmptyStatement`](https://checkstyle.sourceforge.io/checks/coding/emptystatement.html#EmptyStatement)                     | `TBD`           |                |
| 🟢     | [`EqualsAvoidNull`](https://checkstyle.sourceforge.io/checks/coding/equalsavoidnull.html#EqualsAvoidNull)                 | `TBD`           |                |
| 🔴     | [`EqualsHashCode`](https://checkstyle.sourceforge.io/checks/coding/equalshashcode.html#EqualsHashCode)                     |           | Implement proper equals/hashCode pair |
| 🟢     | [`ExplicitInitialization`](https://checkstyle.sourceforge.io/checks/coding/explicitinitialization.html#ExplicitInitialization) | `TBD`           |                |
| 🔴     | [`FallThrough`](https://checkstyle.sourceforge.io/checks/coding/fallthrough.html#FallThrough)                             |           | Add break statements or intentional comments |
| 🟢     | [`FinalLocalVariable`](https://checkstyle.sourceforge.io/checks/coding/finallocalvariable.html#FinalLocalVariable)         |       [`FinalLocalVariable`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/FinalLocalVariable.java)       |                |
| 🔴     | [`HiddenField`](https://checkstyle.sourceforge.io/checks/coding/hiddenfield.html#HiddenField)                             |           | Rename variables or use this. prefix |
| 🔴     | [`IllegalCatch`](https://checkstyle.sourceforge.io/checks/coding/illegalcatch.html#IllegalCatch)                           |           | Change catch block exception types |
| 🔴     | [`IllegalInstantiation`](https://checkstyle.sourceforge.io/checks/coding/illegalinstantiation.html#IllegalInstantiation)   |           | Replace with factory methods |
| 🔴     | [`IllegalThrows`](https://checkstyle.sourceforge.io/checks/coding/illegalthrows.html#IllegalThrows)                       |           | Change throws declarations |
| 🔴     | [`IllegalToken`](https://checkstyle.sourceforge.io/checks/coding/illegaltoken.html#IllegalToken)                         |           | Replace illegal tokens |
| 🔴     | [`IllegalTokenText`](https://checkstyle.sourceforge.io/checks/coding/illegaltokentext.html#IllegalTokenText)               |           | Change token text |
| 🔴     | [`IllegalType`](https://checkstyle.sourceforge.io/checks/coding/illegaltype.html#IllegalType)                             |           | Replace with allowed types |
| 🔴     | [`InnerAssignment`](https://checkstyle.sourceforge.io/checks/coding/innerassignment.html#InnerAssignment)                 |           | Extract assignments to separate statements |
| 🔴     | [`MagicNumber`](https://checkstyle.sourceforge.io/checks/coding/magicnumber.html#MagicNumber)                             |           | Extract to named constants |
| 🔴     | [`MatchXpath`](https://checkstyle.sourceforge.io/checks/coding/matchxpath.html#MatchXpath)                               |           | Context-dependent XPath violations |
| 🔴     | [`MissingCtor`](https://checkstyle.sourceforge.io/checks/coding/missingctor.html#MissingCtor)                             |           | Add explicit constructor |
| 🟢     | [`MissingNullCaseInSwitch`](https://checkstyle.sourceforge.io/checks/coding/missingnullcaseinswitch.html#MissingNullCaseInSwitch) | `TBD`           |                |
| 🟢     | [`MissingSwitchDefault`](https://checkstyle.sourceforge.io/checks/coding/missingswitchdefault.html#MissingSwitchDefault)   | `TBD`           |                |
| 🔴     | [`ModifiedControlVariable`](https://checkstyle.sourceforge.io/checks/coding/modifiedcontrolvariable.html#ModifiedControlVariable) |           | Restructure loop logic |
| 🔴     | [`MultipleStringLiterals`](https://checkstyle.sourceforge.io/checks/coding/multiplestringliterals.html#MultipleStringLiterals) |           | Extract to constants |
| 🟢     | [`MultipleVariableDeclarations`](https://checkstyle.sourceforge.io/checks/coding/multiplevariabledeclarations.html#MultipleVariableDeclarations) | `TBD`           |                |
| 🔴     | [`NestedForDepth`](https://checkstyle.sourceforge.io/checks/coding/nestedfordepth.html#NestedForDepth)                   |           | Requires loop restructuring |
| 🔴     | [`NestedIfDepth`](https://checkstyle.sourceforge.io/checks/coding/nestedifdepth.html#NestedIfDepth)                       |           | Requires conditional restructuring |
| 🔴     | [`NestedTryDepth`](https://checkstyle.sourceforge.io/checks/coding/nestedtrydepth.html#NestedTryDepth)                   |           | Requires exception handling restructuring |
| 🟢     | [`NoArrayTrailingComma`](https://checkstyle.sourceforge.io/checks/coding/noarraytrailingcomma.html#NoArrayTrailingComma) | `TBD`           |                |
| 🔴     | [`NoClone`](https://checkstyle.sourceforge.io/checks/coding/noclone.html#NoClone)                                       |           | Remove clone() method |
| 🟢     | [`NoEnumTrailingComma`](https://checkstyle.sourceforge.io/checks/coding/noenumtrailingcomma.html#NoEnumTrailingComma)     | `TBD`           |                |
| 🔴     | [`NoFinalizer`](https://checkstyle.sourceforge.io/checks/coding/nofinalizer.html#NoFinalizer)


### Headers

| Status | Check                                                                           | Recipe                                                                                                                                      | Coverage Notes             |
|--------|---------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------|----------------------------|
| 🟡     | [`Header`](https://checkstyle.sourceforge.io/checks/header/header.html#Header ) | [`Header`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/Header.java ) | only java files are fixed. |
| 🔴     | [`MultiFileRegexpHeader`](https://checkstyle.sourceforge.io/checks/header/multifileregexpheader.html#MultiFileRegexpHeader) |            | Fix header content |
| 🔴     | [`RegexpHeader`](https://checkstyle.sourceforge.io/checks/header/regexpheader.html#RegexpHeader)                           |            | Fix header content |


### Imports

| Status | Check                                                                                                                        | Recipe                                                                                                                                               | Coverage Notes |
|--------|------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------|----------------|
| 🟢     | [`AvoidStarImport`](https://checkstyle.sourceforge.io/checks/imports/avoidstarimport.html#AvoidStarImport)                 | `TBD`                                                                                                                                                |                |
| 🟢     | [`AvoidStaticImport`](https://checkstyle.sourceforge.io/checks/imports/avoidstaticimport.html#AvoidStaticImport)           | `TBD`                                                                                                                                                |                |
| 🟢     | [`CustomImportOrder`](https://checkstyle.sourceforge.io/checks/imports/customimportorder.html#CustomImportOrder)           | `TBD`                                                                                                                                                |                |
| 🔴     | [`IllegalImport`](https://checkstyle.sourceforge.io/checks/imports/illegalimport.html#IllegalImport)                       |                                                                                                                                                | Replace with allowed imports |
| 🔴     | [`ImportControl`](https://checkstyle.sourceforge.io/checks/imports/importcontrol.html#ImportControl)                       |                                                                                                                                                | Restructure imports per rules |
| 🟢     | [`ImportOrder`](https://checkstyle.sourceforge.io/checks/imports/importorder.html#ImportOrder)                             | `TBD`                                                                                                                                                |                |
| 🟢     | [`RedundantImport`](https://checkstyle.sourceforge.io/checks/imports/redundantimport.html#RedundantImport)                 | [`RedundantImport`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/RedundantImport.java) |                |
| 🟢     | [`UnusedImports`](https://checkstyle.sourceforge.io/checks/imports/unusedimports.html#UnusedImports)                       | `TBD`                                                                                                                                                |                |


### Javadoc Comments

| Status | Check                                                                                                                        | Recipe           | Coverage Notes |
|--------|------------------------------------------------------------------------------------------------------------------------------|------------------|----------------|
| 🟢     | [`AtclauseOrder`](https://checkstyle.sourceforge.io/checks/javadoc/atclauseorder.html#AtclauseOrder)                       | `TBD`            |                |
| 🟢     | [`InvalidJavadocPosition`](https://checkstyle.sourceforge.io/checks/javadoc/invalidjavadocposition.html#InvalidJavadocPosition) | `TBD`            |                |
| 🟢     | [`JavadocBlockTagLocation`](https://checkstyle.sourceforge.io/checks/javadoc/javadocblocktaglocation.html#JavadocBlockTagLocation) | `TBD`            |                |
| 🟢     | [`JavadocContentLocation`](https://checkstyle.sourceforge.io/checks/javadoc/javadoccontentlocation.html#JavadocContentLocation) | `TBD`            |                |
| 🟢     | [`JavadocLeadingAsteriskAlign`](https://checkstyle.sourceforge.io/checks/javadoc/javadocleadingasteriskalign.html#JavadocLeadingAsteriskAlign) | `TBD`            |                |
| 🔴     | [`JavadocMethod`](https://checkstyle.sourceforge.io/checks/javadoc/javadocmethod.html#JavadocMethod)                       |            | Add/fix method documentation |
| 🟢     | [`JavadocMissingLeadingAsterisk`](https://checkstyle.sourceforge.io/checks/javadoc/javadocmissingleadingasterisk.html#JavadocMissingLeadingAsterisk) | `TBD`            |                |
| 🟢     | [`JavadocMissingWhitespaceAfterAsterisk`](https://checkstyle.sourceforge.io/checks/javadoc/javadocmissingwhitespaceafterasterisk.html#JavadocMissingWhitespaceAfterAsterisk) | `TBD`            |                |
| 🔴     | [`JavadocPackage`](https://checkstyle.sourceforge.io/checks/javadoc/javadocpackage.html#JavadocPackage)                   |            | Create package-info.java |
| 🟢     | [`JavadocParagraph`](https://checkstyle.sourceforge.io/checks/javadoc/javadocparagraph.html#JavadocParagraph)             | `TBD`            |                |
| 🟢     | [`JavadocStyle`](https://checkstyle.sourceforge.io/checks/javadoc/javadocstyle.html#JavadocStyle)                         | `TBD`            |                |
| 🟢     | [`JavadocTagContinuationIndentation`](https://checkstyle.sourceforge.io/checks/javadoc/javadoctagcontinuationindentation.html#JavadocTagContinuationIndentation) | `TBD`            |                |
| 🔴     | [`JavadocType`](https://checkstyle.sourceforge.io/checks/javadoc/javadoctype.html#JavadocType)                           |            | Add/fix type documentation |
| 🔴     | [`JavadocVariable`](https://checkstyle.sourceforge.io/checks/javadoc/javadocvariable.html#JavadocVariable)                 |            | Add variable documentation |
| 🔴     | [`MissingJavadocMethod`](https://checkstyle.sourceforge.io/checks/javadoc/missingjavadocmethod.html#MissingJavadocMethod)   |            | Add method documentation |
| 🔴     | [`MissingJavadocPackage`](https://checkstyle.sourceforge.io/checks/javadoc/missingjavadocpackage.html#MissingJavadocPackage) |            | Add package documentation |
| 🔴     | [`MissingJavadocType`](https://checkstyle.sourceforge.io/checks/javadoc/missingjavadoctype.html#MissingJavadocType)         |            | Add type documentation |
| 🔴     | [`NonEmptyAtclauseDescription`](https://checkstyle.sourceforge.io/checks/javadoc/nonemptyatclausedescription.html#NonEmptyAtclauseDescription) |            | Add tag descriptions |
| 🟢     | [`RequireEmptyLineBeforeBlockTagGroup`](https://checkstyle.sourceforge.io/checks/javadoc/requireemptylinebeforeblocktaggroup.html#RequireEmptyLineBeforeBlockTagGroup) | `TBD`            |                |
| 🟢     | [`SingleLineJavadoc`](https://checkstyle.sourceforge.io/checks/javadoc/singlelinejavadoc.html#SingleLineJavadoc)           | `TBD`            |                |
| 🔴     | [`SummaryJavadoc`](https://checkstyle.sourceforge.io/checks/javadoc/summaryjavadoc.html#SummaryJavadoc)                   |            | Rewrite summary sentences |
| 🔴     | [`WriteTag`](https://checkstyle.sourceforge.io/checks/javadoc/writetag.html#WriteTag)                                     |            | Add/fix custom Javadoc tags |


### Metrics

| Status | Check                                                                                                                        | Recipe           | Coverage Notes |
|--------|------------------------------------------------------------------------------------------------------------------------------|------------------|----------------|
| 🔴     | [`BooleanExpressionComplexity`](https://checkstyle.sourceforge.io/checks/metrics/booleanexpressioncomplexity.html#BooleanExpressionComplexity) |            | Requires breaking down complex expressions |
| 🔴     | [`ClassDataAbstractionCoupling`](https://checkstyle.sourceforge.io/checks/metrics/classdataabstractioncoupling.html#ClassDataAbstractionCoupling) |            | Requires architectural changes |
| 🔴     | [`ClassFanOutComplexity`](https://checkstyle.sourceforge.io/checks/metrics/classfanoutcomplexity.html#ClassFanOutComplexity) |            | Requires architectural refactoring |
| 🔴     | [`CyclomaticComplexity`](https://checkstyle.sourceforge.io/checks/metrics/cyclomaticcomplexity.html#CyclomaticComplexity)   |            | Requires method decomposition |
| 🔴     | [`JavaNCSS`](https://checkstyle.sourceforge.io/checks/metrics/javancss.html#JavaNCSS)                                     |            | Requires code simplification |
| 🔴     | [`NPathComplexity`](https://checkstyle.sourceforge.io/checks/metrics/npathcomplexity.html#NPathComplexity)                 |            | Requires method decomposition |

### Miscellaneous

| Status | Check                                                                                                          | Recipe                                                                                                                                                             | Coverage Notes |
|--------|----------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------|
| 🟢     | [`ArrayTypeStyle`](https://checkstyle.sourceforge.io/checks/misc/arraytypestyle.html#ArrayTypeStyle)         | `TBD`                                                                                                                                                              |                |
| 🔴     | [`AvoidEscapedUnicodeCharacters`](https://checkstyle.sourceforge.io/checks/misc/avoidescapedunicodecharacters.html#AvoidEscapedUnicodeCharacters) |                                                                                                                                                                    | Need to determine appropriate replacements |
| 🟢     | [`CommentsIndentation`](https://checkstyle.sourceforge.io/checks/misc/commentsindentation.html#CommentsIndentation) | `TBD`                                                                                                                                                              |                |
| 🔴     | [`DescendantToken`](https://checkstyle.sourceforge.io/checks/misc/descendanttoken.html#DescendantToken)       |                                                                                                                                                                    | Context-dependent token restrictions |
| 🟢     | [`FinalParameters`](https://checkstyle.sourceforge.io/checks/misc/finalparameters.html#FinalParameters)       | `TBD`                                                                                                                                                              |                |
| 🟢     | [`HexLiteralCase`](https://checkstyle.sourceforge.io/checks/misc/hexliteralcase.html#HexLiteralCase)                                       | [`HexLiteralCase`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/HexLiteralCase.java)         |                |
| 🟢     | [`Indentation`](https://checkstyle.sourceforge.io/checks/misc/indentation.html#Indentation)                 | `TBD`                                                                                                                                                              |                |
| 🟢     | [`NewlineAtEndOfFile`](https://checkstyle.sourceforge.io/checks/misc/newlineatendoffile.html#NewlineAtEndOfFile) | [`NewlineAtEndOfFile`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/NewlineAtEndOfFile.java) |                |
| 🔴     | [`NoCodeInFile`](https://checkstyle.sourceforge.io/checks/misc/nocodeinfile.html#NoCodeInFile)             |                                                                                                                                                                    | Add code or remove file |
| 🔴     | [`OrderedProperties`](https://checkstyle.sourceforge.io/checks/misc/orderedproperties.html#OrderedProperties) |                                                                                                                                                                    | Reorder properties |
| 🔴     | [`OuterTypeFilename`](https://checkstyle.sourceforge.io/checks/misc/outertypefilename.html#OuterTypeFilename) |                                                                                                                                                                    | Rename file or class |
| 🔴     | [`TodoComment`](https://checkstyle.sourceforge.io/checks/misc/todocomment.html#TodoComment)                 |                                                                                                                                                                    | Resolve TODO comments |
| 🟢     | [`TrailingComment`](https://checkstyle.sourceforge.io/checks/misc/trailingcomment.html#TrailingComment)       | `TBD`                                                                                                                                                              |                |
| 🔴     | [`Translation`](https://checkstyle.sourceforge.io/checks/misc/translation.html#Translation)                 |                                                                                                                                                                    | Fix property file translations |
| 🟢     | [`UncommentedMain`](https://checkstyle.sourceforge.io/checks/misc/uncommentedmain.html#UncommentedMain)     | `TBD`                                                                                                                                                              |                |
| 🔴     | [`UniqueProperties`](https://checkstyle.sourceforge.io/checks/misc/uniqueproperties.html#UniqueProperties)   |                                                                                                                                                                    | Remove duplicate properties |
| 🟢     | [`UpperEll`](https://checkstyle.sourceforge.io/checks/misc/upperell.html#UpperEll)                         | [`UpperEll`](https://github.com/checkstyle/checkstyle-openrewrite-recipes/blob/main/src/main/java/org/checkstyle/autofix/recipe/UpperEll.java)                     |                |


### Modifiers

| Status | Check                                                                                                                        | Recipe           | Coverage Notes |
|--------|------------------------------------------------------------------------------------------------------------------------------|------------------|----------------|
| 🟢     | [`ClassMemberImpliedModifier`](https://checkstyle.sourceforge.io/checks/modifier/classmemberimpliedmodifier.html#ClassMemberImpliedModifier) | `TBD`            |                |
| 🟢     | [`InterfaceMemberImpliedModifier`](https://checkstyle.sourceforge.io/checks/modifier/interfacememberimpliedmodifier.html#InterfaceMemberImpliedModifier) | `TBD`            |                |
| 🟢     | [`ModifierOrder`](https://checkstyle.sourceforge.io/checks/modifier/modifierorder.html#ModifierOrder)                       | `TBD`            |                |
| 🟢     | [`RedundantModifier`](https://checkstyle.sourceforge.io/checks/modifier/redundantmodifier.html#RedundantModifier)           | `TBD`            |                |

### Naming Conventions

| Status | Check                                                                                                                        | Recipe           | Coverage Notes |
|--------|------------------------------------------------------------------------------------------------------------------------------|------------------|----------------|
| 🔴     | [`AbbreviationAsWordInName`](https://checkstyle.sourceforge.io/checks/naming/abbreviationaswordinname.html#AbbreviationAsWordInName) |            | Requires semantic understanding of abbreviations and context |
| 🟡     | [`AbstractClassName`](https://checkstyle.sourceforge.io/checks/naming/abstractclassname.html#AbstractClassName)             | `TBD`            | Partially covered by renaming abstract class names to match the configured pattern. |
| 🟢     | [`CatchParameterName`](https://checkstyle.sourceforge.io/checks/naming/catchparametername.html#CatchParameterName)           | `TBD`            |                |
| 🟢     | [`ClassTypeParameterName`](https://checkstyle.sourceforge.io/checks/naming/classtypeparametername.html#ClassTypeParameterName) | `TBD`            |                |
| 🟢     | [`ConstantName`](https://checkstyle.sourceforge.io/checks/naming/constantname.html#ConstantName)                           | `TBD`            |                |
| 🔴     | [`IllegalIdentifierName`](https://checkstyle.sourceforge.io/checks/naming/illegalidentifiername.html#IllegalIdentifierName) |            | Rename identifiers |
| 🟢     | [`InterfaceTypeParameterName`](https://checkstyle.sourceforge.io/checks/naming/interfacetypeparametername.html#InterfaceTypeParameterName) | `TBD`            |                |
| 🟢     | [`LambdaParameterName`](https://checkstyle.sourceforge.io/checks/naming/lambdaparametername.html#LambdaParameterName)       | `TBD`            |                |
| 🟢     | [`LocalFinalVariableName`](https://checkstyle.sourceforge.io/checks/naming/localfinalvariablename.html#LocalFinalVariableName) | `TBD`            |                |
| 🟢     | [`LocalVariableName`](https://checkstyle.sourceforge.io/checks/naming/localvariablename.html#LocalVariableName)             | `TBD`            |                |
| 🟢     | [`MemberName`](https://checkstyle.sourceforge.io/checks/naming/membername.html#MemberName)                                 | `TBD`            |                |
| 🟢     | [`MethodName`](https://checkstyle.sourceforge.io/checks/naming/methodname.html#MethodName)                                 | `TBD`            |                |
| 🟢     | [`MethodTypeParameterName`](https://checkstyle.sourceforge.io/checks/naming/methodtypeparametername.html#MethodTypeParameterName) | `TBD`            |                |
| 🔴     | [`PackageName`](https://checkstyle.sourceforge.io/checks/naming/packagename.html#PackageName)                             |            | Rename package |
| 🟢     | [`ParameterName`](https://checkstyle.sourceforge.io/checks/naming/parametername.html#ParameterName)                       | `TBD`            |                |
| 🟢     | [`PatternVariableName`](https://checkstyle.sourceforge.io/checks/naming/patternvariablename.html#PatternVariableName)       | `TBD`            |                |
| 🟢     | [`RecordComponentName`](https://checkstyle.sourceforge.io/checks/naming/recordcomponentname.html#RecordComponentName)       | `TBD`            |                |
| 🟢     | [`RecordTypeParameterName`](https://checkstyle.sourceforge.io/checks/naming/recordtypeparametername.html#RecordTypeParameterName) | `TBD`            |                |
| 🟢     | [`StaticVariableName`](https://checkstyle.sourceforge.io/checks/naming/staticvariablename.html#StaticVariableName)         | `TBD`            |                |
| 🟢     | [`TypeName`](https://checkstyle.sourceforge.io/checks/naming/typename.html#TypeName)                                       | `TBD`            |                |

### Regexp

| Status | Check                                                                                                                        | Recipe | Coverage Notes |
|--------|------------------------------------------------------------------------------------------------------------------------------|--|----------------|
| 🔴     | [`Regexp`](https://checkstyle.sourceforge.io/checks/regexp/regexp.html#Regexp)                                             |  | Context-dependent pattern matching |
| 🔴     | [`RegexpMultiline`](https://checkstyle.sourceforge.io/checks/regexp/regexpmultiline.html#RegexpMultiline)                 |  | Context-dependent pattern fixes |
| 🔴     | [`RegexpOnFilename`](https://checkstyle.sourceforge.io/checks/regexp/regexponfilename.html#RegexpOnFilename)               |  | Rename files |
| 🔴     | [`RegexpSingleline`](https://checkstyle.sourceforge.io/checks/regexp/regexpsingleline.html#RegexpSingleline)             |  | Context-dependent line fixes |
| 🔴     | [`RegexpSinglelineJava`](https://checkstyle.sourceforge.io/checks/regexp/regexpsinglelinejava.html#RegexpSinglelineJava) |  | Context-dependent Java line fixes |

### Size Violations

| Status | Check                                                                                                                        | Recipe           | Coverage Notes |
|--------|------------------------------------------------------------------------------------------------------------------------------|------------------|----------------|
| 🔴     | [`AnonInnerLength`](https://checkstyle.sourceforge.io/checks/sizes/anoninnerlength.html#AnonInnerLength)                   |            | Requires refactoring to named classes |
| 🔴     | [`ExecutableStatementCount`](https://checkstyle.sourceforge.io/checks/sizes/executablestatementcount.html#ExecutableStatementCount) |            | Requires method decomposition |
| 🔴     | [`FileLength`](https://checkstyle.sourceforge.io/checks/sizes/filelength.html#FileLength)                                 |            | Requires file splitting |
| 🔴     | [`LambdaBodyLength`](https://checkstyle.sourceforge.io/checks/sizes/lambdabodylength.html#LambdaBodyLength)               |            | Extract lambda to method |
| 🔴     | [`LineLength`](https://checkstyle.sourceforge.io/checks/sizes/linelength.html#LineLength)                                 |            | Requires line breaking decisions |
| 🔴     | [`MethodCount`](https://checkstyle.sourceforge.io/checks/sizes/methodcount.html#MethodCount)                             |            | Requires class decomposition |
| 🔴     | [`MethodLength`](https://checkstyle.sourceforge.io/checks/sizes/methodlength.html#MethodLength)                           |            | Requires method decomposition |
| 🔴     | [`OuterTypeNumber`](https://checkstyle.sourceforge.io/checks/sizes/outertypenumber.html#OuterTypeNumber)                 |            | Split types into separate files |
| 🔴     | [`ParameterNumber`](https://checkstyle.sourceforge.io/checks/sizes/parameternumber.html#ParameterNumber)                 |            | Reduce parameter count |
| 🔴     | [`RecordComponentNumber`](https://checkstyle.sourceforge.io/checks/sizes/recordcomponentnumber.html#RecordComponentNumber) |            | Reduce record components |

### Whitespace

| Status | Check                                                                                                                        | Recipe           | Coverage Notes |
|--------|------------------------------------------------------------------------------------------------------------------------------|------------------|----------------|
| 🟢     | [`EmptyForInitializerPad`](https://checkstyle.sourceforge.io/checks/whitespace/emptyforinitialispad.html#EmptyForInitializerPad) | `TBD`            |                |
| 🟢     | [`EmptyForIteratorPad`](https://checkstyle.sourceforge.io/checks/whitespace/emptyforiteratorpad.html#EmptyForIteratorPad) | `TBD`            |                |
| 🟢     | [`EmptyLineSeparator`](https://checkstyle.sourceforge.io/checks/whitespace/emptylineseparator.html#EmptyLineSeparator)     | `TBD`            |                |
| 🟢     | [`FileTabCharacter`](https://checkstyle.sourceforge.io/checks/whitespace/filetabcharacter.html#FileTabCharacter)           | `TBD`            |                |
| 🟢     | [`GenericWhitespace`](https://checkstyle.sourceforge.io/checks/whitespace/genericwhitespace.html#GenericWhitespace)       | `TBD`            |                |
| 🟢     | [`MethodParamPad`](https://checkstyle.sourceforge.io/checks/whitespace/methodparampad.html#MethodParamPad)                 | `TBD`            |                |
| 🔴     | [`NoLineWrap`](https://checkstyle.sourceforge.io/checks/whitespace/nolinewrap.html#NoLineWrap)                           |            | Requires line unwrapping decisions |
| 🟢     | [`NoWhitespaceAfter`](https://checkstyle.sourceforge.io/checks/whitespace/nowhitespaceafter.html#NoWhitespaceAfter)       | `TBD`            |                |
| 🟢     | [`NoWhitespaceBefore`](https://checkstyle.sourceforge.io/checks/whitespace/nowhitespaceto.html#NoWhitespaceBefore)         | `TBD`            |                |
| 🟢     | [`NoWhitespaceBeforeCaseDefaultColon`](https://checkstyle.sourceforge.io/checks/whitespace/nowhitespacebeforecasedefaultcolon.html#NoWhitespaceBeforeCaseDefaultColon) | `TBD`            |                |
| 🟢     | [`OperatorWrap`](https://checkstyle.sourceforge.io/checks/whitespace/operatorwrap.html#OperatorWrap)                     | `TBD`            |                |
| 🟢     | [`ParenPad`](https://checkstyle.sourceforge.io/checks/whitespace/parenpad.html#ParenPad)                                 | `TBD`            |                |
| 🟢     | [`SeparatorWrap`](https://checkstyle.sourceforge.io/checks/whitespace/separatorwrap.html#SeparatorWrap)                   | `TBD`            |                |
| 🟢     | [`SingleSpaceSeparator`](https://checkstyle.sourceforge.io/checks/whitespace/singlespaceseparator.html#SingleSpaceSeparator) | `TBD`            |                |
| 🟢     | [`TypecastParenPad`](https://checkstyle.sourceforge.io/checks/whitespace/typecastparenpad.html#TypecastParenPad)         | `TBD`            |                |
| 🟢     | [`WhitespaceAfter`](https://checkstyle.sourceforge.io/checks/whitespace/whitespaceafter.html#WhitespaceAfter)             | `TBD`            |                |
| 🟢     | [`WhitespaceAround`](https://checkstyle.sourceforge.io/checks/whitespace/whitespacearound.html#WhitespaceAround)           | `TBD`            |                |