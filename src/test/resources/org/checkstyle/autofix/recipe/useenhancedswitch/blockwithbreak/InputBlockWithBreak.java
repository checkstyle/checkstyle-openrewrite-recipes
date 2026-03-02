/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.useenhancedswitch.blockwithbreak;

public class InputBlockWithBreak {

    void doSomething(String param) {
    }

    void blockCasesWithBreak(int x) {
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1:
                {
                    doSomething("one");
                    break;
                }
            case 2:
                {
                    doSomething("start");
                    doSomething("end");
                    break;
                }
            default:
                {
                    doSomething("default");
                    break;
                }
        }
    }

    String multiStatementBlockCases(int x) {
        String result;
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1:
                {
                    doSomething("first");
                    result = "one";
                    break;
                }
            case 2:
                {
                    result = "two";
                    break;
                }
            default:
                {
                    doSomething("last");
                    result = "other";
                    break;
                }
        }
        return result;
    }

    void mixedBlockAndPlainCases(int x) {
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1:
                doSomething("plain");
                break;
            case 2:
                {
                    doSomething("blocked");
                    break;
                }
            default:
                break;
        }
    }
}
