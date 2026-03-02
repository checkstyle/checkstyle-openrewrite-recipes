/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.useenhancedswitch.mutationtests15;

public class OutputMutationTests15 {
    enum Color { RED, GREEN, BLUE }
    enum SmallEnum { A, B }

    int testThrowUnreachable(int x) {
        return switch (x) {
            case 1 -> throw new RuntimeException("err");
            default -> 0;
        };
    }

    void testAdjustNoAssignment(int x) {
        switch (x) {
            case 1 -> System.out.println("one");
            case 2 -> System.out.println("two");
            default -> System.out.println("default");
        }
    }

    void testAdjustNonIdentifierAssignment(int x) {
        int[] arr = new int[2];
        switch (x) {
            case 1 -> arr[0] = 1;
            default -> arr[0] = 0;
        }
    }

    int testReturnWithThrowCase(int x) {
        return switch (x) {
            case 1 -> 10;
            case 2 -> 20;
            default -> throw new IllegalArgumentException("bad");
        };
    }

    int testReturnExpression(int x) {
        return switch (x) {
            case 1 -> x + 1;
            case 2 -> x + 2;
            default -> 0;
        };
    }

    void testPartialEnum(SmallEnum e) {
        switch (e) {
            case A -> System.out.println("A");
        }
    }

    int testFullyExhaustiveReturn(Color c) {
        return switch (c) {
            case RED -> 1;
            case GREEN -> 2;
            case BLUE -> 3;
        };
    }

    void testBlockEndSpace(int x) {
        switch (x) {
            case 1 -> {
                System.out.println("a");
                System.out.println("b");
            }
            default -> System.out.println("c");
        }
    }

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

    int testCollectHandled(Color c) {
        return switch (c) {
            case RED, GREEN -> 1;
            case BLUE -> 2;
        };
    }

    void testEmptyDefault(int x) {
        switch (x) {
            case 1 -> System.out.println("one");
            default -> {}
        }
    }
}
