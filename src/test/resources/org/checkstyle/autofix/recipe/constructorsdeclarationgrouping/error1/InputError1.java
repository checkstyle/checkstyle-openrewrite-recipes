/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module
        name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck"/>
  </module>
  <module name="SuppressionXpathSingleFilter">
      <property name="checks" value="ConstructorsDeclarationGrouping"/>
      <property name="query" value="//CTOR_DEF[./PARAMETERS/PARAMETER_DEF/TYPE/LITERAL_INT and not(./PARAMETERS/PARAMETER_DEF/TYPE/IDENT)]"/>
    </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.error1;

/**
 * The constructor InputError1(int x) ends up at the violation location after first cycle.
 * It should remain at the same position after second cycle.
 */
public class InputError1 {

    int x;

    InputError1() {}

    InputError1(String s) {}

    void foo() {}

    InputError1(int x) {}

    InputError1(String s, int x) {}
    // violation above 'Constructors should be grouped together.*'
}
