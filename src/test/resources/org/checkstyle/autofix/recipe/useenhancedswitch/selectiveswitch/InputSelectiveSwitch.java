/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.useenhancedswitch.selectiveswitch;

public class InputSelectiveSwitch {

    void doSomething(String param) {
    }

    void alreadyEnhanced(int x) {
        switch (x) {
            case 1 -> doSomething("one");
            case 2 -> doSomething("two");
            default -> doSomething("default");
        }
    }

    void needsConversion(int x) {
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1:
                doSomething("one");
                break;
            case 2:
                doSomething("two");
                break;
            default:
                doSomething("default");
                break;
        }
    }
}
