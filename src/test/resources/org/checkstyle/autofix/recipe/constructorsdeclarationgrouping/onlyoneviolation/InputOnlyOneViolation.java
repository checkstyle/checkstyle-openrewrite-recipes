/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.onlyoneviolation;

/**
 * Exactly one constructor is separated from the sole initial constructor.
 * Verifies the simplest possible violation: a single isolated constructor.
 */
public class InputOnlyOneViolation {

    InputOnlyOneViolation() {}

    void foo() {}

    InputOnlyOneViolation(int x) {} // violation 'Constructors should be grouped together'

    void bar() {}

}
