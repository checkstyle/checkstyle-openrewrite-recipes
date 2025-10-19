/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.HexLiteralCaseCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.hexliterialcase.hexliteralcase;

public class OutputHexLiteralCase {
    int h = 0xB;
    int w = 0xB;
    long d = 0XF123L;
    byte b1  = 0x1B;
    byte b2  = 0x1B;
    short s2 = 0xF5F;
    int i1 = 0x11 + 0xABC;
    long l1 = 0x7FFF_FFFF_FFFF_FFFFL;
    long l2 = 0x7FFF_AAA_BBB_DDDL;
    int val = 0x1B, sum = 0xFF;
    int x1 = 223, x2 = 0xA1;

    private static void functionCall() {
        functionCall2(0x7FFF, 0xAB);
    }
    private static void functionCall2(int first, int second) {
        System.out.println("First: " + first);
        System.out.println("Second: " + second);
    }

}
