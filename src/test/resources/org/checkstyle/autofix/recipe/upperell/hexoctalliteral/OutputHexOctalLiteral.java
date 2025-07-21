/*xml
<module name="Checker">
  <module name="com.puppycrawl.tools.checkstyle.filters.SuppressionXpathSingleFilter">
    <property name="checks" value="UpperEll"/>
    <property name="query" value="//NUM_LONG[@text='0xDEADBEFl' or @text='01234l']"/>
  </module>
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.UpperEllCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.upperell.hexoctalliteral;

public class OutputHexOctalLiteral {
    private long hexLower = 0x1ABCL;
    private long hexUpper = 0X2DEFL;
    private long octal = 0777L;
    private long binary = 0b1010L;
    private long decimal = 12345L;

    public void calculateValues() {
        long hexResult = 0xDEADBEEFL + 0xDEADBEFl; //suppressed violation for 0xDEADBEFl
        long octalResult = 01234l + 0xDEADBEEFL;   //suppressed violation for 01234l
        long binaryResult = 0b11110000L;
    }
}
