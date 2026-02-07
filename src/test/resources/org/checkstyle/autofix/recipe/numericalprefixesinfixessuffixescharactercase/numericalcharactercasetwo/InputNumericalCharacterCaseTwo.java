/*xml
<module name="Checker">
  <module name="com.puppycrawl.tools.checkstyle.filters.SuppressionXpathSingleFilter">
    <property name="checks" value="NumericalPrefixesInfixesSuffixesCharacterCase"/>
    <property name="query" value="//NUM_LONG[@text='0XDEADBEFl' or @text='0X12l']"/>
  </module>
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.NumericalPrefixesInfixesSuffixesCharacterCaseCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.numericalprefixesinfixessuffixescharactercase.numericalcharactercasetwo;

public class InputNumericalCharacterCaseTwo {

    public void calculateValues() {
        // violation below, 'Numerical prefix should be in lowercase.'
        long hexResult = 0XDEADBEEFl + 0XDEADBEFl; //suppressed violation for 0XDEADBEFl
        // violation below, 'Numerical prefix should be in lowercase.'
        long octalResult = 0X12l + 0XDEADBEEFl;   //suppressed violation for 0X12l
    }
}
