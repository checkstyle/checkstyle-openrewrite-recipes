/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.useenhancedswitch.basicswitchstatement;

public class InputBasicSwitchStatement {

    void doSomething(String param) {
    }

    void singleStatementCases(int x) {
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1:
                doSomething("one");
                break;
            case 2:
                doSomething("two");
                break;
            case 3:
                doSomething("three");
                break;
            case 4:
                break;
            case 5:
                doSomething("five");
                break;
            default:
                doSomething("default");
                break;
        }
    }

    void multiStatementCases(int x) {
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1:
                doSomething("start");
                doSomething("end");
                break;
            case 2:
                doSomething("only");
                break;
        }
    }

    void emptyDefaultCase(int x) {
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1:
                doSomething("one");
                break;
            default:
                break;
        }
    }

    void trailingEmptyCases(int x) {
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1:
                doSomething("one");
                break;
            case 2:
            case 3:
        }
    }
}
