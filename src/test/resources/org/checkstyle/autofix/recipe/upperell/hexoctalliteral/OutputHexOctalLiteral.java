package org.checkstyle.autofix.recipe.upperell.hexoctalliteral;

public class OutputHexOctalLiteral {
    private long hexLower = 0x1ABCL;
    private long hexUpper = 0X2DEFL;
    private long octal = 0777L;
    private long binary = 0b1010L;
    private long decimal = 12345L;

    public void calculateValues() {
        long hexResult = 0xDEADBEEFL + 0xDEADBEFl; //suppressed violation for 0xDEADBEFl
        long octalResult = 01234L + 0xDEADBEEFl;   //suppressed violation for 0xDEADBEFl
        long binaryResult = 0b11110000L;
    }
}
