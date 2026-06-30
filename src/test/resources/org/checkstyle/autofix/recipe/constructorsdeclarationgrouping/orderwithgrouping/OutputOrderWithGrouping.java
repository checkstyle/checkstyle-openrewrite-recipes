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

public class OutputOrderWithGrouping {

    OutputOrderWithGrouping() {}

    OutputOrderWithGrouping(int x) {}

    OutputOrderWithGrouping(String s, int x) {}

    void foo() {}
}
