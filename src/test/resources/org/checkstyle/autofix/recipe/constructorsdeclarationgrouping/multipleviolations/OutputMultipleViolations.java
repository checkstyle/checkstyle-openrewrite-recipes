/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.multipleviolations;

/** Class where multiple constructors are scattered across the class body. */
public class OutputMultipleViolations {

    OutputMultipleViolations() {}

    OutputMultipleViolations(String b) {}

    OutputMultipleViolations(int a, String b) {}

    int a;

    String b;

    void foo() {}

}
