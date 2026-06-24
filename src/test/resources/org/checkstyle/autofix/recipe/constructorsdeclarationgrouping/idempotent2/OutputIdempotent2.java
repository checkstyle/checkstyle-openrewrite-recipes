/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.idempotent2;

/**
 * The constructor Idempotent2(String b) ends up at the violation location after first cycle.
 * It should remain at the same position after second cycle.
 */
public class OutputIdempotent2 {

    OutputIdempotent2() {}

    OutputIdempotent2(int a) {}

    OutputIdempotent2(String b) {}

    OutputIdempotent2(int a, String b) {}

    int a;

    String b;

    void foo() {}

}
