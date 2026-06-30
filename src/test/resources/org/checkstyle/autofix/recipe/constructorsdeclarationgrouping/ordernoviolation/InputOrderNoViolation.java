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

public class InputOrderNoViolation {

    InputOrderNoViolation() {}

    InputOrderNoViolation(int x) {}

    InputOrderNoViolation(String s) {}
}
