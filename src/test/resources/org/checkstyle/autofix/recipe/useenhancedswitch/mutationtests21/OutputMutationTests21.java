/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.useenhancedswitch.mutationtests21;

public class OutputMutationTests21 {
    enum Color { RED, GREEN, BLUE }

    void testDifferentVariableAssignment(int x) {
        int y;
        int z;
        switch (x) {
            case 1 -> y = 1;
            case 2 -> z = 1;
            default -> y = 0;
        }
    }

    void testEmptyCase(int x) {
        int y;
        switch (x) {
            case 1 -> y = 1;
            case 2 -> {}
            default -> y = 0;
        }
    }

    enum OtherColor { RED, GREEN }
    void testMultipleEnums(Color c, OtherColor oc) {
        int x = switch (c) {
            case RED -> 1;
            case GREEN -> 2;
            case BLUE -> 3;
        };
    }

    void testContinueInCase(int x) {
        for (int i = 0; i < 10; i++) {
            switch (x) {
                case 1 -> {
                    if (x > 0) {
                        continue;
                    }
                    System.out.println("one");}
                case 2 -> System.out.println("two");
                default -> {}
            }
        }
    }

    void testLabeledBreakInCase(int x) {
        outer:
        for (int i = 0; i < 10; i++) {
            switch (x) {
                case 1 -> {
                    if (x > 0) {
                        break outer;
                    }
                    System.out.println("one");}
                case 2 -> System.out.println("two");
                default -> {}
            }
        }
    }

    void testThrowInAssignment(int x) {
        int y = switch (x) {
            case 1 -> 1;
            default -> throw new RuntimeException("bad");
        };
    }

    void testNoNewlineIndent(int x) {
        int y = switch (x) {
            case 1 -> 1;
            default -> 0;
        };
    }
}
