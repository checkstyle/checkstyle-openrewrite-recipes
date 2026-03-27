/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.error2;

/**
 * The constructor InputError2(String b) ends up at the violation location after first cycle.
 * It should remain at the same position after second cycle.
 */
public class OutputError2 {

    OutputError2() {}

    OutputError2(int a) {}

    OutputError2(String b) {}

    OutputError2(int a, String b) {}

    int a;

    String b;

    void foo() {}

}
