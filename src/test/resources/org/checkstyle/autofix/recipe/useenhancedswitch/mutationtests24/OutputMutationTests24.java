/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.useenhancedswitch.mutationtests24;

public class OutputMutationTests24 {
    void testContinueControlFlow(int x) {
        for (int i = 0; i < 10; i++) {
            int y;
            switch (x) {
                case 1 -> {
                    if (i > 5) continue;
                    y = 1;}
                default -> y = 0;
            }
        }
    }

    void testReturnControlFlowInAssignment(int x) {
        int y;
        switch (x) {
            case 1 -> {
                if (x > 100) return;
                y = 1;}
            default -> y = 0;
        }
    }

    void testLabeledBreakControlFlowInAssignment(int x) {
        outer:
        for (int i = 0; i < 5; i++) {
            int y;
            switch (x) {
                case 1 -> {
                    if (x > 100) break outer;
                    y = 1;}
                default -> y = 0;
            }
        }
    }

    void testBlockIndent(int x) {
        switch (x) {
            case 1 -> {
                    int y = 1;
                    System.out.println(y);
                }
            default -> {
                    int y = 0;
                    System.out.println(y);
                }
        }
    }

    void testAssignmentBlockYield(int x) {
        int y = switch (x) {
            case 1 -> {
                int z = x + 1;
                yield z;
            }
            default -> 0;
        };
    }

    void testSingleAssignment(int x) {
        int y = switch (x) {
            case 1 -> 100;
            case 2 -> 200;
            default -> 0;
        };
    }
}
