/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.useenhancedswitch.mutationtests2;

public class OutputMutationTests2 {

    void doSomething(String p) {}

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
}
