/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module
        name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.samelinedeclarations;

/**
 * Multiple constructors or methods are declared on the same line.
 * Verifies that ungrouped constructors are moved correctly.
 */
public class InputSameLineDeclarations {

    int x;

    InputSameLineDeclarations() {} InputSameLineDeclarations(String s) {}

    void foo() {}    InputSameLineDeclarations(int x) {}
    // violation above 'Constructors should be grouped together.*'

    InputSameLineDeclarations(String s, int x) {}
    // violation above 'Constructors should be grouped together.*'
}
