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

/**
 * A field is declared before the first constructor and constructors are out
 * of order by parameter count. Verifies that the first constructor index is
 * correctly located when it is not at statement index 0.
 */
public class OutputOrderWithFieldBeforeFirstConstructor {

    private int field = 0;

    OutputOrderWithFieldBeforeFirstConstructor(int x) {}

    OutputOrderWithFieldBeforeFirstConstructor(int x, boolean b) {}
}
