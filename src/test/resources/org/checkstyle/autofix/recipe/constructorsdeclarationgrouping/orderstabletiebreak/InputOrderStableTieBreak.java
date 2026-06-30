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

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.orderstabletiebreak;

/**
 * Multiple constructors have the same parameter count. Verifies that the
 * sort is stable - constructors with equal parameter counts preserve
 * their original relative order.
 */
public class InputOrderStableTieBreak {

    InputOrderStableTieBreak(int y) {}

    InputOrderStableTieBreak(long x) {}

    InputOrderStableTieBreak() {}
    // violation above 'Constructors should be ordered.*'

    void foo() {}

    InputOrderStableTieBreak(String s) {} // violation 'Constructors should be grouped together.*'
    // violation above 'Constructors should be ordered.*'
}
