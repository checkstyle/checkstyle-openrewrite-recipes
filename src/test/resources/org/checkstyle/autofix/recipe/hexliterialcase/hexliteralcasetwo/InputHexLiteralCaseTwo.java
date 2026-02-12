/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.HexLiteralCaseCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.hexliterialcase.hexliteralcasetwo;

public class InputHexLiteralCaseTwo {
    float f = 0x1.fp1f;  // violation  'Should use uppercase hexadecimal letters.'
    double d = 0x1abcde.fp1d; // violation  'Should use uppercase hexadecimal letters.'

    float f1 = 0x1a_e_f.1p1f; // violation  'Should use uppercase hexadecimal letters.'
    double d1 = 0x1a_b_c_d_e.1p1d; // violation  'Should use uppercase hexadecimal letters.'

    float f2 = 0xab.1P1f; // violation  'Should use uppercase hexadecimal letters.'
}
