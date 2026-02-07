/*xml
<module name="Checker">
  <module name="com.puppycrawl.tools.checkstyle.filters.SuppressionXpathSingleFilter">
    <property name="checks" value="NumericalPrefixesInfixesSuffixesCharacterCase"/>
    <property name="query" value="//NUM_FLOAT[@text='0XABCD.1p1F' or @text='0X.1p2f']"/>
  </module>
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.NumericalPrefixesInfixesSuffixesCharacterCaseCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.numericalprefixesinfixessuffixescharactercase.numericalcharactercasethree;

public class InputNumericalCharacterCaseThree {
    int temp = 123;
    public void calculateValues() {
        // violation below, 'Numerical prefix should be in lowercase.'
        float hexResult = 0XABC.p1F + 0XABCD.1p1F; //suppressed violation for 0XABCD.1p1F
        // violation below, 'Numerical prefix should be in lowercase.'
        float octalResult = 0X.1p2f + 0X.12P1F;   //suppressed violation for 0X.1p2f
    }
}
