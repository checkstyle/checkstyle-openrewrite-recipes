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

public class OutputNumericalCharacterCaseTwo {

    public void calculateValues() {
        long hexResult = 0xDEADBEEFl + 0XDEADBEFl; //suppressed violation for 0XDEADBEFl
        long octalResult = 0X12l + 0xDEADBEEFl;   //suppressed violation for 0X12l
    }
}
