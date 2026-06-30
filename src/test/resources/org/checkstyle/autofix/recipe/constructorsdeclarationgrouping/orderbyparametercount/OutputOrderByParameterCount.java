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

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.orderbyparametercount;

/**
 * Two constructors are out of param-count order: a 1-param constructor
 * comes before a 0-param constructor. Verifies that they are swapped to
 * increasing parameter count when {@code orderByIncreasingParameterCount}
 * is enabled.
 */
public class OutputOrderByParameterCount {

    OutputOrderByParameterCount() {}

    OutputOrderByParameterCount(String s) {}
}
