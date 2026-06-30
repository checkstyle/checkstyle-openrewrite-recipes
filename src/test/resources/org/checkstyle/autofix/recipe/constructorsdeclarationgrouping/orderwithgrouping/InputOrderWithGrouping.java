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

public class InputOrderWithGrouping {

    InputOrderWithGrouping(String s, int x) {}

    InputOrderWithGrouping(int x) {}
    // violation above 'Constructors should be ordered.*'

    void foo() {}

    InputOrderWithGrouping() {} // violation 'Constructors should be grouped together.*'
    // violation above 'Constructors should be ordered.*'
}
