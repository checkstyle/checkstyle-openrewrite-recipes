/*
com.puppycrawl.tools.checkstyle.checks.UpperEllCheck

*/


package org.checkstyle.autofix.recipe.upperell.hexoctalliteral;

public class InputHexOctalLiteral {
    private long hexLower = 0x1ABCl;
    private long hexUpper = 0X2DEFl;
    private long octal = 0777l;
    private long binary = 0b1010l;
    private long decimal = 12345l;

    public void calculateValues() {
        long hexResult = 0xDEADBEEFl + 0xDEADBEFl; //suppressed violation for 0xDEADBEFl
        long octalResult = 01234l + 0xDEADBEEFl;   //suppressed violation for 0xDEADBEFl
        long binaryResult = 0b11110000l;
    }
}