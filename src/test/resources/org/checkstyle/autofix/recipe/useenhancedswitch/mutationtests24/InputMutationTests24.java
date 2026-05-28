/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.useenhancedswitch.mutationtests24;

public class InputMutationTests24 {
    void testContinueControlFlow(int x) {
        for (int i = 0; i < 10; i++) {
            int y;
            switch (x) { // violation 'Switch can be replaced with enhanced switch.'
                case 1:
                    if (i > 5) continue;
                    y = 1;
                    break;
                default:
                    y = 0;
                    break;
            }
        }
    }

    void testReturnControlFlowInAssignment(int x) {
        int y;
        switch (x) { // violation 'Switch can be replaced with enhanced switch.'
            case 1:
                if (x > 100) return;
                y = 1;
                break;
            default:
                y = 0;
                break;
        }
    }

    void testLabeledBreakControlFlowInAssignment(int x) {
        outer:
        for (int i = 0; i < 5; i++) {
            int y;
            switch (x) { // violation 'Switch can be replaced with enhanced switch.'
                case 1:
                    if (x > 100) break outer;
                    y = 1;
                    break;
                default:
                    y = 0;
                    break;
            }
        }
    }

    void testBlockIndent(int x) {
        switch (x) { // violation 'Switch can be replaced with enhanced switch.'
            case 1: {
                    int y = 1;
                    System.out.println(y);
                    break;
                }
            default: {
                    int y = 0;
                    System.out.println(y);
                    break;
                }
        }
    }

    void testAssignmentBlockYield(int x) {
        int y;
        switch (x) { // violation 'Switch can be replaced with enhanced switch.'
            case 1: {
                int z = x + 1;
                y = z;
                break;
            }
            default:
                y = 0;
                break;
        }
    }

    void testSingleAssignment(int x) {
        int y;
        switch (x) { // violation 'Switch can be replaced with enhanced switch.'
            case 1:
                y = 100;
                break;
            case 2:
                y = 200;
                break;
            default:
                y = 0;
                break;
        }
    }
}
