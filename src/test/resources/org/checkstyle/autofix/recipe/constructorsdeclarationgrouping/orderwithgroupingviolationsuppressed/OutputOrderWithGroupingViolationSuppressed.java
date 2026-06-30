/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module
        name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck">
      <property name="orderByIncreasingParameterCount" value="true"/>
    </module>
    <module name="SuppressionXpathSingleFilter">
      <property name="checks" value="ConstructorsDeclarationGroupingCheck"/>
      <property name="message" value="Constructors should be grouped together.*"/>
      <property name="query"
          value="//CTOR_DEF[not(./PARAMETERS/PARAMETER_DEF)
                or count(./PARAMETERS/PARAMETER_DEF)=3]"/>
    </module>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.orderwithgroupingviolationsuppressed;

public class OutputOrderWithGroupingViolationSuppressed {

    OutputOrderWithGroupingViolationSuppressed() {}

    OutputOrderWithGroupingViolationSuppressed(int x) {}

    OutputOrderWithGroupingViolationSuppressed(int x, boolean b) {}

    void foo() {}

    OutputOrderWithGroupingViolationSuppressed(String s, int x, int y) {}
}
