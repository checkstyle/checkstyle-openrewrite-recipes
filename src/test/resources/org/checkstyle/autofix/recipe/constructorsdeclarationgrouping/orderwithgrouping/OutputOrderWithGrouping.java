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

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.orderwithgrouping;

/**
 * Constructors are out of param-count order AND separated by a method.
 * Verifies that all non-suppressed constructors are grouped together and
 * sorted by increasing parameter count when the ordering property is enabled.
 */
public class OutputOrderWithGrouping {

    OutputOrderWithGrouping() {}

    OutputOrderWithGrouping(int x) {}

    OutputOrderWithGrouping(String s, int x) {}

    void foo() {}
}
