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

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.suppressedviolations;

public class OutputSuppressedViolations {

    int x;

    OutputSuppressedViolations() {}

    OutputSuppressedViolations(String s) {}

    OutputSuppressedViolations(String s, int x) {}

    void foo() {}

    OutputSuppressedViolations(int x) {}
}
