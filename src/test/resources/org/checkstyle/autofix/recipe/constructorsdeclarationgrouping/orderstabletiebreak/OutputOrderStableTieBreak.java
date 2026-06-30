/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module
        name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck">
      <property name="orderByIncreasingParameterCount" value="true"/>
    </module>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.orderstabletiebreak;

/**
 * Multiple constructors have the same parameter count. Verifies that the
 * sort is stable - constructors with equal parameter counts preserve
 * their original relative order.
 */
public class OutputOrderStableTieBreak {

    OutputOrderStableTieBreak() {}

    OutputOrderStableTieBreak(int y) {}

    OutputOrderStableTieBreak(long x) {}

    OutputOrderStableTieBreak(String s) {}

    void foo() {}
}
