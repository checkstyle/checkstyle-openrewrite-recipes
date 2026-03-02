/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.useenhancedswitch.blockwithbreak;

public class OutputBlockWithBreak {

    void doSomething(String param) {
    }

    void blockCasesWithBreak(int x) {
        switch (x) {
            case 1 -> doSomething("one");
            case 2 -> {
                doSomething("start");
                doSomething("end");
            }
            default -> doSomething("default");
        }
    }

    String multiStatementBlockCases(int x) {
        String result = switch (x) {
            case 1 -> {
                doSomething("first");
                yield "one";
            }
            case 2 -> "two";
            default -> {
                doSomething("last");
                yield "other";
            }
        };
        return result;
    }

    void mixedBlockAndPlainCases(int x) {
        switch (x) {
            case 1 -> doSomething("plain");
            case 2 -> doSomething("blocked");
            default -> {}
        }
    }
}
