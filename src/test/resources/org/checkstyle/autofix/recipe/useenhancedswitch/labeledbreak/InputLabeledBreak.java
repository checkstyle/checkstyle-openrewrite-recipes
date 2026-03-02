/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.useenhancedswitch.labeledbreak;

public class InputLabeledBreak {

    void doSomething(String param) {
    }

    void labeledBreakInSwitch(int x) {
        outerLoop:
        for (int i = 0; i < 10; i++) {
            // violation below, 'Switch can be replaced with enhanced switch'
            switch (x) {
                case 1:
                    doSomething("one");
                    break outerLoop;
                case 2:
                    doSomething("two");
                    break;
                default:
                    doSomething("default");
                    break;
            }
        }
    }
}
