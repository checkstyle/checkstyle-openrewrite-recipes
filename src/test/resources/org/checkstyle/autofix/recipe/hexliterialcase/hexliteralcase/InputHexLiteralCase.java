/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.HexLiteralCaseCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.hexliterialcase.hexliteralcase;

public class InputHexLiteralCase {
    int h = 0xb;                       // violation  'Should use uppercase hexadecimal letters.'
    int w = 0xB;
    long d = 0Xf123L;                  // violation  'Should use uppercase hexadecimal letters.'
    byte b1  = 0x1b;                   // violation  'Should use uppercase hexadecimal letters.'
    byte b2  = 0x1B;
    short s2 = 0xF5f;                  // violation  'Should use uppercase hexadecimal letters.'
    int i1 = 0x11 + 0xabc;             // violation  'Should use uppercase hexadecimal letters.'
    long l1 = 0x7fff_ffff_ffff_ffffL;
    // violation above 'Should use uppercase hexadecimal letters.'
    long l2 = 0x7FFF_AAA_bBB_DDDL; // violation 'Should use uppercase hexadecimal letters.'
    // violation below 'Should use uppercase hexadecimal letters.'
    int val = 0x1b, sum = 0xff;    // violation 'Should use uppercase hexadecimal letters.'
    int x1 = 223, x2 = 0xa1;       // violation 'Should use uppercase hexadecimal letters.'

    private static void functionCall() {
        // violation below 'Should use uppercase hexadecimal letters.'
        functionCall2(0x7fff, 0xab); // violation 'Should use uppercase hexadecimal letters.'
    }
    private static void functionCall2(int first, int second) {
        System.out.println("First: " + first);
        System.out.println("Second: " + second);
    }

    long l3 = 0xABcl; // violation 'Should use uppercase hexadecimal letters.'
}
