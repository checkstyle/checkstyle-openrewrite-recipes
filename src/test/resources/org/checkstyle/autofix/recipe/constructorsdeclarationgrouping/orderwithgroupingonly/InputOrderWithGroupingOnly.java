/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module
        name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck">
      <property name="orderByIncreasingParameterCount" value="true"/>
    </module>
    <module name="SuppressionXpathSingleFilter">
      <property name="checks" value="ConstructorsDeclarationGrouping"/>
      <property name="message" value="Constructors should be ordered.*"/>
      <property name="query"
          value="//CTOR_DEF[./PARAMETERS/PARAMETER_DEF/TYPE/IDENT]"/>
    </module>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.orderwithgroupingonly;

/**
 * The last constructor is separated by a method and has its ordering
 * violation suppressed. It should be grouped but not reordered - moving
 * one position forward to join the constructor group.
 */
public class InputOrderWithGroupingOnly {

    InputOrderWithGroupingOnly() {}

    InputOrderWithGroupingOnly(boolean b, int x) {}

    void foo(int a, int b, int c) {}

    InputOrderWithGroupingOnly(String s) {} // violation 'Constructors should be grouped together.*'
}
