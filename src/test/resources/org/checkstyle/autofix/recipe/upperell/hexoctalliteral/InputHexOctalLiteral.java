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
    private long hexLower = 0x1ABCl;    // violation 'Should use uppercase 'L'.'
    private long hexUpper = 0X2DEFl;    // violation 'Should use uppercase 'L'.'
    private long octal = 0777l;         // violation 'Should use uppercase 'L'.'
    private long binary = 0b1010l;      // violation 'Should use uppercase 'L'.'
    private long decimal = // comment preceding a target element
                           12345l;      // violation 'Should use uppercase 'L'.'

    public void calculateValues() {
        // violation below, 'Should use uppercase 'L'.'
        long hexResult = 0xDEADBEEFl + 0xDEADBEFl; //suppressed violation for 0xDEADBEFl
        // violation below, 'Should use uppercase 'L'.'
        long octalResult = 01234l + 0xDEADBEEFl;   //suppressed violation for 01234l
        long binaryResult = 0b11110000l;           // violation 'Should use uppercase 'L'.'
    }
}
