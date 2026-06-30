/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.idempotent2;

/**
 * Constructors that end up at violation locations after the first cycle of reordering
 * must not be wrongly moved again in a second cycle. Tests that already-moved
 * constructors stay put.
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
