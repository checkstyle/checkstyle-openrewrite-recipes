/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.onlyoneviolation;

/** Class with exactly one constructor that is separated from the group. */
public class OutputOnlyOneViolation {

    OutputOnlyOneViolation() {}

    OutputOnlyOneViolation(int x) {}

    void foo() {}

    void bar() {}

}
