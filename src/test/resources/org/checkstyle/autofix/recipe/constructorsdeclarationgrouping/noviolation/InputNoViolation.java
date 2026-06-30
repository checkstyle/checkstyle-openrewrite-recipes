/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.noviolation;

/**
 * All constructors are already grouped together with no violations.
 * Verifies that the Recipe makes no changes when the code is already compliant.
 */
public class InputNoViolation {

    int x;

    InputNoViolation() {}

    InputNoViolation(String s) {}

    InputNoViolation(int x) {}

    void foo() {}

    void bar() {}

}
