/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.NumericalPrefixesInfixesSuffixesCharacterCaseCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.numericalprefixesinfixessuffixescharactercase.numericalcharactercase;

public class InputNumericalCharacterCase {

    int temp = 0X123; // violation 'Numerical prefix should be in lowercase.'
    int hex1 = 0X1A; // violation 'Numerical prefix should be in lowercase.'
    int hex2 = 0x1A;

    int bin1 = 0B1010; // violation 'Numerical prefix should be in lowercase.'
    int bin2 = 0b1010;

    float exp1 = 1.23E3f; // violation 'Numerical infix should be in lowercase.'
    float exp2 = 1.23e3f;

    double hexExp1 = 0x1.3P2; // violation 'Numerical infix should be in lowercase.'
    double hexExp2 = 0x1.3p2;

    float suf1 = 10F; // violation 'Numerical suffix should be in lowercase.'
    float suf2 = 10f;

    double suf3 = 10D; // violation 'Numerical suffix should be in lowercase.'
    double suf4 = 10d;

    float mix1 = 1.2E3F; // violation 'Numerical infix should be in lowercase.'
    float mix2 = 1.2e3f;

    double mix3 = 0x1.3P2D; // violation 'Numerical infix should be in lowercase.'
    double mix4 = 0x1.3p2D; // violation 'Numerical suffix should be in lowercase.'
    double mix5 = 0x1.3p2d;
    float mix6 = 0X1f_fff; // violation 'Numerical prefix should be in lowercase.'
    float mix7 = 0XABC.1P2F; // violation 'Numerical prefix should be in lowercase.'

    byte b1 = 0B101010; // violation 'Numerical prefix should be in lowercase.'

    int ok1 = 0x1F;
    double ok2 = 123.456;
    double ok3 = 1 / 5432.0;
    double ok4 = 0x1E2.2p2d;
    long ok5 = 123L;
    double ok6 = 2.0 + 2 / 5432.0;

    long l1 = 0XABCL; // violation 'Numerical prefix should be in lowercase.'
}
