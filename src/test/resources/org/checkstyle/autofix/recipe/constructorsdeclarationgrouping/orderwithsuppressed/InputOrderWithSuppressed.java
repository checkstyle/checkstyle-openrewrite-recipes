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
    <property name="query"
        value="//CTOR_DEF[./PARAMETERS/PARAMETER_DEF/TYPE/LITERAL_INT
                  and not(./PARAMETERS/PARAMETER_DEF/TYPE/IDENT)]"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.orderwithsuppressed;

public class InputOrderWithSuppressed {

    InputOrderWithSuppressed(String s) {}

    InputOrderWithSuppressed() {}
    // violation above 'Constructors should be ordered.*'

    void foo() {}

    InputOrderWithSuppressed(int x) {}
}
