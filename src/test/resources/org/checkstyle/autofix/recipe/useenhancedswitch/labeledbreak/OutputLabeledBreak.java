/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.useenhancedswitch.labeledbreak;

public class OutputLabeledBreak {

    void doSomething(String param) {
    }

    void labeledBreakInSwitch(int x) {
        outerLoop:
        for (int i = 0; i < 10; i++) {
            switch (x) {
                case 1 -> {
                    doSomething("one");
                    break outerLoop;
                }
                case 2 -> doSomething("two");
                default -> doSomething("default");
            }
        }
    }
}
