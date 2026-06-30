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
          value="//CTOR_DEF[./PARAMETERS/PARAMETER_DEF/TYPE/LITERAL_LONG]"/>
    </module>
    <module name="SuppressionXpathSingleFilter">
      <property name="checks" value="ConstructorsDeclarationGrouping"/>
      <property name="message" value="Constructors should be grouped together.*"/>
      <property name="query"
          value="//CTOR_DEF[./PARAMETERS/PARAMETER_DEF/TYPE/LITERAL_SHORT]"/>
    </module>
    <module name="SuppressionXpathSingleFilter">
      <property name="checks" value="ConstructorsDeclarationGrouping"/>
      <property name="message" value="Constructors should be ordered.*"/>
      <property name="query"
          value="//CTOR_DEF[./PARAMETERS/PARAMETER_DEF/TYPE/LITERAL_BYTE]"/>
    </module>
    <module name="SuppressionXpathSingleFilter">
      <property name="checks" value="ConstructorsDeclarationGrouping"/>
      <property name="query"
          value="//CTOR_DEF[not(./PARAMETERS/PARAMETER_DEF)]"/>
    </module>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.complex;

/**
 * A complex scenario with 10 declarations - constructors with different
 * parameter counts, a method, and selective suppression of ordering and
 * grouping violations via {@code SuppressionXpathSingleFilter}.
 * Verifies that the recipe respects suppression granularity.
 */
public class InputComplex {

    InputComplex(int x) {}

    InputComplex(String s) {}

    InputComplex(int x, String s, boolean b) {}

    InputComplex(long l) {}

    InputComplex(double d, int x) {}
    // violation above 'Constructors should be ordered.*'

    void foo() {}

    InputComplex(float f, String s) {} // violation 'Constructors should be grouped together.*'
    // violation above 'Constructors should be ordered.*'

    InputComplex(short s) {}
    // violation above 'Constructors should be ordered.*'

    InputComplex(byte b, char c) {}
    // violation above 'Constructors should be grouped together.*'

    InputComplex() {}
}
