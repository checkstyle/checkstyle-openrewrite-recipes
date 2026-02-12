/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.HexLiteralCaseCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.hexliterialcase.hexliteralcasetwo;

public class OutputHexLiteralCaseTwo {
    float f = 0x1.Fp1f;
    double d = 0x1ABCDE.Fp1d;

    float f1 = 0x1A_E_F.1p1f;
    double d1 = 0x1A_B_C_D_E.1p1d;

    float f2 = 0xAB.1P1f;
}
