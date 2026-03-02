/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.useenhancedswitch.basicswitchstatement;

public class OutputBasicSwitchStatement {

    void doSomething(String param) {
    }

    void singleStatementCases(int x) {
        switch (x) {
            case 1 -> doSomething("one");
            case 2 -> doSomething("two");
            case 3 -> doSomething("three");
            case 4 -> {}
            case 5 -> doSomething("five");
            default -> doSomething("default");
        }
    }

    void multiStatementCases(int x) {
        switch (x) {
            case 1 -> {
                doSomething("start");
                doSomething("end");
            }
            case 2 -> doSomething("only");
        }
    }

    void emptyDefaultCase(int x) {
        switch (x) {
            case 1 -> doSomething("one");
            default -> {}
        }
    }

    void trailingEmptyCases(int x) {
        switch (x) {
            case 1 -> doSomething("one");
            case 2, 3 -> {}
        }
    }
}
