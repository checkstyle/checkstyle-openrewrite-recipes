/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.multipleviolations;

/**
 * Three constructors are each separated by a different member (field, field, method).
 * Verifies that all violating constructors are collected and inserted in their
 * original relative order after the initial constructor group.
 */
public class InputMultipleViolations {

    InputMultipleViolations() {}

    int a;

    String b;

    InputMultipleViolations(String b) {} // violation 'Constructors should be grouped together'

    void foo() {}

    InputMultipleViolations(int a, String b) {} // violation 'Constructors should be grouped together'

}
