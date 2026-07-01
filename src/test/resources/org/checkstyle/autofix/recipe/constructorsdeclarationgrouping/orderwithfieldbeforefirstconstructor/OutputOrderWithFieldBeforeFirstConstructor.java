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

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.orderwithfieldbeforefirstconstructor;

public class OutputOrderWithFieldBeforeFirstConstructor {

    private int field = 0;

    OutputOrderWithFieldBeforeFirstConstructor(int x) {}

    OutputOrderWithFieldBeforeFirstConstructor(int x, boolean b) {}
}
