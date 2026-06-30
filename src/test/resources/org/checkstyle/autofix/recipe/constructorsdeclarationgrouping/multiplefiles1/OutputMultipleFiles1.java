/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module
        name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck"/>
  </module>
  <module name="SuppressionXpathSingleFilter">
      <property name="checks" value="ConstructorsDeclarationGrouping"/>
      <property name="query" value="//CLASS_DEF[./IDENT[@text='InputMultipleFiles2'] or ./IDENT[@text='OutputMultipleFiles2']]//CTOR_DEF"/>
    </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.multiplefiles1;

/**
 * Multiple files.
 * Verifies that violating constructors from the correct files are moved.
 */
public class OutputMultipleFiles1 {

    int x;

    OutputMultipleFiles1() {}

    OutputMultipleFiles1(String s) {}

    OutputMultipleFiles1(int x) {}

    void foo() {}
}
