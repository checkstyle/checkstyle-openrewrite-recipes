/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module
        name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck">
      <property name="orderByIncreasingParameterCount" value="true"/>
    </module>
  </module>
  <module name="SuppressionXpathSingleFilter">
    <property name="checks" value="ConstructorsDeclarationGrouping"/>
    <property name="query" value="//CLASS_DEF[./IDENT[@text='NestedClass']]//CTOR_DEF"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.ordernoviolationoutoforder;

/**
 * All violations are suppressed in the nested class, while the outer class have
 * violations. Verifies that only the outer class is modified.
 */
public class OutputOrderNoViolationOutOfOrder {

    OutputOrderNoViolationOutOfOrder() {}

    OutputOrderNoViolationOutOfOrder(int x) {}

    static class NestedClass {
        NestedClass(int x) {}
        NestedClass() {}
    }
}
