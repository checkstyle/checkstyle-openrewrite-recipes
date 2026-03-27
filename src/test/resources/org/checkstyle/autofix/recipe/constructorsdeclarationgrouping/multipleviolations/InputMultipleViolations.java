/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.multipleviolations;

/** Class where multiple constructors are scattered across the class body. */
public class InputMultipleViolations {

    InputMultipleViolations() {}

    int a;

    String b;

    InputMultipleViolations(String b) {} // violation 'Constructors should be grouped together'

    void foo() {}

    InputMultipleViolations(int a, String b) {} // violation 'Constructors should be grouped together'

}
