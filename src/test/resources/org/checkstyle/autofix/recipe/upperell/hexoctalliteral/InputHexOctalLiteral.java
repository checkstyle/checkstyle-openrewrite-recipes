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

public class InputHexOctalLiteral {
    private long hexLower = 0x1ABCl;  //violation
    private long hexUpper = 0X2DEFl;  //violation
    private long octal = 0777l;       //violation
    private long binary = 0b1010l;    //violation
    private long decimal = 12345l;    //violation

    public void calculateValues() {
        //violation below
        long hexResult = 0xDEADBEEFl + 0xDEADBEFl; //suppressed violation for 0xDEADBEFl
        //violation below
        long octalResult = 01234l + 0xDEADBEEFl;   //suppressed violation for 01234l
        long binaryResult = 0b11110000l;           //violation
    }
}
