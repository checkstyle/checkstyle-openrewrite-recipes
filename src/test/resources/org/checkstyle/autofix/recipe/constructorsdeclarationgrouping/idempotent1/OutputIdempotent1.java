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
 * The constructor Idempotent1(int x) ends up at the violation location after first cycle.
 * It should remain at the same position after second cycle.
 */
public class OutputIdempotent1 {

    int x;

    OutputIdempotent1() {}

    OutputIdempotent1(String s) {}

    OutputIdempotent1(String s, int x) {}

    void foo() {}

    OutputIdempotent1(int x) {}
}
