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

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.idempotent1;

/**
 * Constructors that end up at violation locations after the first cycle of reordering
 * must not be wrongly moved again in a second cycle. Tests that XPath-suppressed
 * constructors at violation positions are correctly ignored.
 */
public class OutputIdempotent1 {

    int x;

    OutputIdempotent1() {}

    OutputIdempotent1(String s) {}

    OutputIdempotent1(String s, int x) {}

    void foo() {}

    OutputIdempotent1(int x) {}
}
