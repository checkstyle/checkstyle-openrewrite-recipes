/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.useenhancedswitch.mutationtests14;

public class OutputMutationTests14 {
    enum Color { RED, GREEN, BLUE }

    void test(int x) {
        int y;
        switch (x) {
            case 1, 2 -> y = 1;
            case 3 -> {
                y = 2;
                x = 3;
            }
            default -> y = 0;
        }
    }

    void testEnum(Color c) {
        int x = switch (c) {
            case RED -> 1;
            case GREEN -> 2;
            case BLUE -> 3;
        };
    }

    void testIndent(int x) {
        int y = switch (x) {
            case 1 -> 1;
            default -> 0;
        };
    }

    int testReturn(int x) {
        return switch (x) {
            case 1 -> 1;
            default -> 0;
        };
    }

    void testIndent2(int x) {
        int y = switch (x) {
            case 1 -> 1;
            default -> 0;
        };
    }

    void testIndent3(int x) {
        int y = switch (x) {
            case 1 -> 1;
            default -> 0;
        };
    }

    void testNoNewline(int x) {
        switch (x) {
            case 1 -> x = 1;
            default -> {}
        }
    }

    void testIndentVisitorNoNewline(int x) {
        int y;
        switch (x) {
            case 1 -> {
                y = 1; x = 2;}
            default -> y = 0;
        }
    }

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

    void testNegativeControlFlow(int x) {
        int y;
        switch (x) {
            case 1 -> {
                if (x > 0) return;
                y = 1;}
            default -> y = 0;
        }
    }

    void testNegativeAssignment(int x) {
        int[] arr = new int[1];
        switch (x) {
            case 1 -> arr[0] = 1;
            default -> arr[0] = 0;
        }
    }

    enum SmallEnum { A, B }
    void testNegativeEnum(SmallEnum e) {
        int x;
        switch (e) {
            case A -> x = 1;
        }
    }

    void testNegativeAssignmentNoAssign(int x) {
        int y;
        switch (x) {
            case 1 -> System.out.println(1);
            default -> y = 0;
        }
    }

    int testNegativeReturnNoDefault(SmallEnum e) {
        switch (e) {
            case A -> { return 1;}
        }
        return 0;
    }

    int testNegativeReturnControlFlow(int x) {
        switch (x) {
            case 1 -> {
                if (x > 0) {
                    for (int i=0; i<10; i++) {
                        if (i == 5) break;
                    }
                }
                return 1;}
            default -> { return 0;
            }
        }
    }

    void testAssignmentFallthrough(int x) {
        int y = switch (x) {
            case 1, 2 -> 1;
            default -> 0;
        };
    }

    void testAssignmentBlock(int x) {
        int y = switch (x) {
            case 1 -> {
                System.out.println(1);
                yield 1;
            }
            default -> 0;
        };
    }

    void testNegativeAssignmentNonIdentifier(int x) {
        int y;
        switch (x) {
            case 1 -> this.y = 1;
            default -> y = 0;
        }
    }

    private int y;

    void testExhaustiveEnumNoDefault(Color e) {
        int x = switch (e) {
            case RED -> 1;
            case GREEN -> 2;
            case BLUE -> 3;
        };
    }

    void testIndentNoNewline(int x) {
        int y = switch (x) {
            case 1 -> 1;
            default -> 0;
        };
    }

    int testExhaustiveWithDefault(Color e) {
        return switch (e) {
            case RED -> 1;
            case GREEN -> 2;
            case BLUE -> 3;
        };
    }

    int testNonExhaustiveEnumNoDefault(SmallEnum e) {
        switch (e) {
            case A -> {
                return 1;}
        }
        return 0;
    }

    int testExhaustiveEnumReturnNoDefault(Color e) {
        return switch (e) {
            case RED -> 1;
            case GREEN -> 2;
            case BLUE -> 3;
        };
    }

    void testEnumDefaultOnly(Color e) {
        int x = switch (e) {
            default -> 1;
        };
    }

    void testContinueInSwitch() {
        for (int i = 0; i < 5; i++) {
            switch (i) {
                case 1 -> {
                    continue;}
                default -> {
                    int x = i;
                }
            }
        }
    }
}

