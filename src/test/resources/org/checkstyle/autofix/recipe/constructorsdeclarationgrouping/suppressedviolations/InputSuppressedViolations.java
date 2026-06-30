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

/**
 * Not all constructors are grouped together, but violations are suppressed.
 * Verifies that no modification is made.
 */
public class InputSuppressedViolations {

    int x;

    InputSuppressedViolations() {}

    InputSuppressedViolations(String s) {}

    void foo() {}

    InputSuppressedViolations(String s, int x) {}
    // violation above 'Constructors should be grouped together.*'

    InputSuppressedViolations(int x) {}
}
