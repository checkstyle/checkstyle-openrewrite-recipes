/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.NumericalPrefixesInfixesSuffixesCharacterCaseCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.numericalprefixesinfixessuffixescharactercase.numericalcharactercase;

public class OutputNumericalCharacterCase {

    int temp = 0x123;
    int hex1 = 0x1A;
    int hex2 = 0x1A;

    int bin1 = 0b1010;
    int bin2 = 0b1010;

    float exp1 = 1.23e3f;
    float exp2 = 1.23e3f;

    double hexExp1 = 0x1.3p2;
    double hexExp2 = 0x1.3p2;

    float suf1 = 10f;
    float suf2 = 10f;

    double suf3 = 10d;
    double suf4 = 10d;

    float mix1 = 1.2e3f;
    float mix2 = 1.2e3f;

    double mix3 = 0x1.3p2d;
    double mix4 = 0x1.3p2d;
    double mix5 = 0x1.3p2d;
    float mix6 = 0x1f_fff;
    float mix7 = 0xABC.1p2f;

    byte b1 = 0b101010;

    int ok1 = 0x1F;
    double ok2 = 123.456;
    double ok3 = 1 / 5432.0;
    double ok4 = 0x1E2.2p2d;
    long ok5 = 123L;
    double ok6 = 2.0 + 2 / 5432.0;

    long l1 = 0xABCL;
}
