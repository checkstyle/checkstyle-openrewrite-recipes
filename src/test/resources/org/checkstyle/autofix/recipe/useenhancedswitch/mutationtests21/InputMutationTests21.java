/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.useenhancedswitch.mutationtests21;

public class InputMutationTests21 {
    enum Color { RED, GREEN, BLUE }

    void testDifferentVariableAssignment(int x) {
        int y;
        int z;
        switch (x) { // violation 'Switch can be replaced with enhanced switch.'
            case 1:
                y = 1;
                break;
            case 2:
                z = 1;
                break;
            default:
                y = 0;
                break;
        }
    }

    void testEmptyCase(int x) {
        int y;
        switch (x) { // violation 'Switch can be replaced with enhanced switch.'
            case 1:
                y = 1;
                break;
            case 2:
                break;
            default:
                y = 0;
                break;
        }
    }

    enum OtherColor { RED, GREEN }
    void testMultipleEnums(Color c, OtherColor oc) {
        int x;
        switch (c) { // violation 'Switch can be replaced with enhanced switch.'
            case RED: x = 1; break;
            case GREEN: x = 2; break;
            case BLUE: x = 3; break;
        }
    }

    void testContinueInCase(int x) {
        for (int i = 0; i < 10; i++) {
            switch (x) { // violation 'Switch can be replaced with enhanced switch.'
                case 1:
                    if (x > 0) {
                        continue;
                    }
                    System.out.println("one");
                    break;
                case 2:
                    System.out.println("two");
                    break;
                default:
                    break;
            }
        }
    }

    void testLabeledBreakInCase(int x) {
        outer:
        for (int i = 0; i < 10; i++) {
            switch (x) { // violation 'Switch can be replaced with enhanced switch.'
                case 1:
                    if (x > 0) {
                        break outer;
                    }
                    System.out.println("one");
                    break;
                case 2:
                    System.out.println("two");
                    break;
                default:
                    break;
            }
        }
    }

    void testThrowInAssignment(int x) {
        int y;
        switch (x) { // violation 'Switch can be replaced with enhanced switch.'
            case 1:
                y = 1;
                break;
            default:
                throw new RuntimeException("bad");
        }
    }

    void testNoNewlineIndent(int x) {
        int y;
        switch (x) { // violation 'Switch can be replaced with enhanced switch.'
            case 1: y = 1; break;
            default: y = 0; break;
        }
    }
}
