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
public class InputOrderWithGrouping {

    InputOrderWithGrouping(String s, int x) {}

    InputOrderWithGrouping(int x) {}
    // violation above 'Constructors should be ordered.*'

    void foo() {}

    InputOrderWithGrouping() {} // violation 'Constructors should be grouped together.*'
    // violation above 'Constructors should be ordered.*'
}
