/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.onlyoneviolation;

/** Class with exactly one constructor that is separated from the group. */
public class InputOnlyOneViolation {

    InputOnlyOneViolation() {}

    void foo() {}

    InputOnlyOneViolation(int x) {} // violation 'Constructors should be grouped together'

    void bar() {}

}
