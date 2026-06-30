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

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.ordernoviolation;

/**
 * Constructors are already grouped and correctly ordered by increasing
 * parameter count. Verifies that the recipe makes no changes.
 */
public class InputOrderNoViolation {

    InputOrderNoViolation() {}

    InputOrderNoViolation(int x) {}

    InputOrderNoViolation(String s) {}
}
