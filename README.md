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

_No checks analyzed yet_


### Block Checks

_No checks analyzed yet_


### Class Design

_No checks analyzed yet_


### Coding

| Status | Check                                                                                                              | Recipe | Coverage Notes                                                                           |
|--------|--------------------------------------------------------------------------------------------------------------------|--------|------------------------------------------------------------------------------------------|
| 🟢     | [`FinalLocalVariable`](https://checkstyle.sourceforge.io/checks/coding/finallocalvariable.html#FinalLocalVariable) | `TBD`  |                                                                                          |
| 🔴     | [`MagicNumber`](https://checkstyle.sourceforge.io/checks/coding/magicnumber.html#MagicNumber)                      |        | it requires contextual understanding to replace literals with meaningful named constants |


### Headers

| Status | Check                                                                           | Recipe           | Coverage Notes |
|--------|---------------------------------------------------------------------------------|------------------|----------------|
| 🟢     | [`Header`](https://checkstyle.sourceforge.io/checks/header/header.html#Header ) | `TBD`            |                |



### Imports


| Status | Check                                                                                                       | Recipe           | Coverage Notes |
|--------|-------------------------------------------------------------------------------------------------------------|------------------|----------------|
| 🟢     | [`RedundantImport`](https://checkstyle.sourceforge.io/checks/imports/redundantimport.html#RedundantImport ) | `TBD`            |                |



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

_No checks analyzed yet_


### Regexp

_No checks analyzed yet_


### Size Violations

_No checks analyzed yet_


### Whitespace

_No checks analyzed yet_

