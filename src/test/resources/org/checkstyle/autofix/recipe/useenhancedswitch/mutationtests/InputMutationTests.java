/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.useenhancedswitch.mutationtests;

public class InputMutationTests {

    void doSomething(String p) {}

    void singleSwitchInBlock(int x, int y) {
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1:
                {
                    // violation below, 'Switch can be replaced with enhanced switch'
                    switch (y) {
                        case 1: break;
                    }
                    break;
                }
        }
    }

    void emptyBlock(int x) {
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1:
                {
                    break;
                }
        }
    }

    void weirdlyIndented(int x) {
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1:
                {
doSomething("no indent");
                    break;
                }
        }
    }

    void multiBreaksInBlock(int x) {
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1:
                {
                    if (x > 0) break;
                    doSomething("multi");
                    break;
                }
        }
    }

    void underIndented(int x) {
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1:
                {
  doSomething("less indent 1");
  doSomething("less indent 2");
                    break;
                }
        }
    }

    void multiLinePrefix(int x) {
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1:
                /* comment */
                {
                    doSomething("a");
                    doSomething("b");
                    break;
                }
        }
    }

    void blankLines(int x) {
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1:

                {
                    doSomething("blankLine 1");
                    doSomething("blankLine 2");
                    break;
                }
        }
    }

    void sameLineStatements(int x) {
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1:      { doSomething("same");
                doSomething("next");
                break;
            }
        }
    }

    void blankLinesCase(int x) {
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {

            case 1: {
                doSomething("blankCase 1");
                doSomething("blankCase 2");
                break;
            }
        }
    }

    String returnOnlySwitch(int id) {
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (id) {
            case 1:
                return "REQUEST";
            case 2:
                return "REPLY";
            default:
                throw new IllegalArgumentException("Unknown id " + id);
        }
    }

    String multiStatementYield(int id) {
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (id) {
            case 1:
                System.out.println("log");
                return "one";
            case 2:
                return "two";
            default:
                throw new RuntimeException();
        }
    }

    void assignmentSwitch(int x) {
        String s;
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1:
                s = "one";
                break;
            case 2:
                System.out.println("two");
                s = "two";
                break;
            default:
                s = "default";
        }
    }

    void assignmentSwitchWithThrow(int x) {
        String s;
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1:
                s = "one";
                break;
            default:
                throw new RuntimeException("bad");
        }
    }

    void assignmentSwitchFallthrough(int x) {
        int val;
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1:
            case 2:
                val = 10;
                break;
            case 3:
                val = 30;
                break;
            default:
                val = 0;
                break;
        }
    }

    void assignmentSwitchBlock(int x) {
        String s;
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1: {
                s = "one";
                break;
            }
            case 2: {
                System.out.println("two");
                s = "two";
                break;
            }
            default: {
                s = "default";
                break;
            }
        }
    }

    void assignmentSwitchBlockMulti(int x) {
        String s;
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1: {
                System.out.println("log1");
                System.out.println("log2");
                s = "one";
                break;
            }
            default: {
                s = "default";
                break;
            }
        }
    }

    void assignmentSwitchInitialized(int x) {
        String s = "init";
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1:
                s = "one";
                break;
            default:
                s = "default";
                break;
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
