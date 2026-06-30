/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module
        name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.simple;

/**
 * Baseline test: two constructors separated by a method and a field.
 * Verifies that all violating constructors are moved to follow the initial group.
 */
public class InputSimple {

    int x;

    InputSimple() {}

    InputSimple(String s) {}

    void foo() {}

    InputSimple(int x) {}
    // violation above 'Constructors should be grouped together.*'
}
