# checkstyle-openrewrite-recipes
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

### Block Checks

_No checks analyzed yet_


### Class Design

| Status | Check                                                                                      | Recipe           | Coverage Notes |
|--------|--------------------------------------------------------------------------------------------|------------------|----------------|
| 🟢     | [`FinalClass`](https://checkstyle.sourceforge.io/checks/design/finalclass.html#FinalClass) | `TBD`            |                |



### Coding

| Status | Check                                                                                                                            | Recipe | Coverage Notes                                                                           |
|--------|----------------------------------------------------------------------------------------------------------------------------------|--------|------------------------------------------------------------------------------------------|
| 🟢     | [`FinalLocalVariable`](https://checkstyle.sourceforge.io/checks/coding/finallocalvariable.html#FinalLocalVariable)               | `TBD`  |                                                                                          |
| 🔴     | [`MagicNumber`](https://checkstyle.sourceforge.io/checks/coding/magicnumber.html#MagicNumber)                                    |        | it requires contextual understanding to replace literals with meaningful named constants |
| 🟢     | [`UnusedLocalVariable `](https://checkstyle.sourceforge.io/checks/coding/unusedlocalvariable.html#UnusedLocalVariable)           | `TBD`  |                                                                                          |
| 🟢     | [`UnnecessaryParentheses  `](https://checkstyle.sourceforge.io/checks/coding/unnecessaryparentheses.html#UnnecessaryParentheses) | `TBD`  |                                                                                          |



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

