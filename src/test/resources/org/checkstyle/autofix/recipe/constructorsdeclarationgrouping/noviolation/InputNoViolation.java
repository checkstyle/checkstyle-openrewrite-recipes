/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.noviolation;

/** Class where all constructors are already properly grouped together. */
public class InputNoViolation {

    int x;

    InputNoViolation() {}

    InputNoViolation(String s) {}

    InputNoViolation(int x) {}

    void foo() {}

    void bar() {}

}
