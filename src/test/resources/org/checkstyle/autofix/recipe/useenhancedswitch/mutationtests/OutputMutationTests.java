/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.useenhancedswitch.mutationtests;

public class OutputMutationTests {

    void doSomething(String p) {}

    void singleSwitchInBlock(int x, int y) {
        switch (x) {
            case 1 -> {
                    switch (y) {
                    case 1 -> {}
                }
            }
        }
    }

    void emptyBlock(int x) {
        switch (x) {
            case 1 -> {}
        }
    }

    void weirdlyIndented(int x) {
        switch (x) {
            case 1 -> doSomething("no indent");
        }
    }

    void multiBreaksInBlock(int x) {
        switch (x) {
            case 1 -> {
                if (x > 0) break;
                doSomething("multi");
            }
        }
    }

    void underIndented(int x) {
        switch (x) {
            case 1 -> {
doSomething("less indent 1");
doSomething("less indent 2");
            }
        }
    }

    void multiLinePrefix(int x) {
        switch (x) {
            case 1 -> {
                doSomething("a");
                doSomething("b");
            }
        }
    }

    void blankLines(int x) {
        switch (x) {
            case 1 -> {
                doSomething("blankLine 1");
                doSomething("blankLine 2");
            }
        }
    }

    void sameLineStatements(int x) {
        switch (x) {
            case 1 -> { doSomething("same");
                doSomething("next");
            }
        }
    }

    void blankLinesCase(int x) {
        switch (x) {

            case 1 -> {
                doSomething("blankCase 1");
                doSomething("blankCase 2");
            }
        }
    }

    String returnOnlySwitch(int id) {
        return switch (id) {
            case 1 -> "REQUEST";
            case 2 -> "REPLY";
            default -> throw new IllegalArgumentException("Unknown id " + id);
        };
    }

    String multiStatementYield(int id) {
        return switch (id) {
            case 1 -> {
                System.out.println("log");
                yield "one";
            }
            case 2 -> "two";
            default -> throw new RuntimeException();
        };
    }

    void assignmentSwitch(int x) {
        String s = switch (x) {
            case 1 -> "one";
            case 2 -> {
                System.out.println("two");
                yield "two";
            }
            default -> "default";
        };
    }

    void assignmentSwitchWithThrow(int x) {
        String s = switch (x) {
            case 1 -> "one";
            default -> throw new RuntimeException("bad");
        };
    }

    void assignmentSwitchFallthrough(int x) {
        int val = switch (x) {
            case 1, 2 -> 10;
            case 3 -> 30;
            default -> 0;
        };
    }

    void assignmentSwitchBlock(int x) {
        String s = switch (x) {
            case 1 -> "one";
            case 2 -> {
                System.out.println("two");
                yield "two";
            }
            default -> "default";
        };
    }

    void assignmentSwitchBlockMulti(int x) {
        String s = switch (x) {
            case 1 -> {
                System.out.println("log1");
                System.out.println("log2");
                yield "one";
            }
            default -> "default";
        };
    }

    void assignmentSwitchInitialized(int x) {
        String s = "init";
        switch (x) {
            case 1 -> s = "one";
            default -> s = "default";
        }
    }

    void fallThroughSwitch(int x) {
        switch (x) {
            case 1:
                System.out.println("log1");
            case 2:
                System.out.println("log2");
                break;
        }
    }
}
