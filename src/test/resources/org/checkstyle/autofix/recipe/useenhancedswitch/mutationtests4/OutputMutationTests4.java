/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.useenhancedswitch.mutationtests4;

public class OutputMutationTests4 {

    enum Color { RED, GREEN, BLUE }

    void enumExhaustiveNoDefault(Color color) {
        int x = 0;
        switch (color) {
            case RED -> x = 1;
            case GREEN -> x = 2;
            case BLUE -> x = 3;
        }
        System.out.println(x);
    }

    void assignmentBlockMultiStmt(Color color) {
        int val = switch (color) {
            case RED -> {
                System.out.println("red");
                yield 1;
            }
            case GREEN -> {
                System.out.println("green");
                yield 2;
            }
            default -> 0;
        };
        System.out.println(val);
    }

    void assignmentFallthrough(int x) {
        String val = switch (x) {
            case 1, 2 -> "one or two";
            case 3 -> "three";
            default -> "other";
        };
        System.out.println(val);
    }

    int returnWithThrow(int x) {
        return switch (x) {
            case 1 -> 10;
            case 2 -> 20;
            default -> throw new IllegalArgumentException("bad");
        };
    }

    void blockSingleStmt(int x) {
        switch (x) {
            case 1 -> System.out.println("one");
            default -> System.out.println("default");
        }
    }

    int returnBlockMultiStmt(int x) {
        return switch (x) {
            case 1 -> {
                System.out.println("processing");
                yield 100;
            }
            default -> {
                System.out.println("default processing");
                yield 0;
            }
        };
    }

    void assignmentArrowBlock(int x) {
        int val = switch (x) {
            case 1 -> 10;
            case 2 -> {
                System.out.println("two");
                yield 20;
            }
            default -> 0;
        };
        System.out.println(val);
    }

    int blockReturnExpr(int x) {
        return switch (x) {
            case 1 -> {
                System.out.println("case1");
                yield 1;
            }
            case 2 -> 2;
            default -> {
                System.out.println("default");
                yield 0;
            }
        };
    }

    void assignmentWithThrow(int x) {
        String val = switch (x) {
            case 1 -> "one";
            default -> throw new RuntimeException("error");
        };
        System.out.println(val);
    }

    void multiStatement(int x) {
        switch (x) {
            case 1 -> {
                System.out.println("a");
                System.out.println("b");
            }
            default -> System.out.println("default");
        }
    }
}
