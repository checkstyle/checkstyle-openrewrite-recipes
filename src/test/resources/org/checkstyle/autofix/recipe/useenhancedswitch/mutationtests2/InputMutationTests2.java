/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.useenhancedswitch.mutationtests2;

public class InputMutationTests2 {

    void doSomething(String p) {}

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
}
