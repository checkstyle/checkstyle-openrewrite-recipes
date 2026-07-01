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

public class InputOrderWithFieldBeforeFirstConstructor {

    private int field = 0;

    InputOrderWithFieldBeforeFirstConstructor(int x, boolean b) {}

    InputOrderWithFieldBeforeFirstConstructor(int x) {}
    // violation above 'Constructors should be ordered.*'
}
