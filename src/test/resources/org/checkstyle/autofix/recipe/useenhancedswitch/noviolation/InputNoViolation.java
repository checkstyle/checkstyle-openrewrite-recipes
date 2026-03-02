/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.useenhancedswitch.noviolation;

public class InputNoViolation {

    void doSomething(String param) {
    }

    void alreadyEnhanced(int x) {
        switch (x) {
            case 1 -> doSomething("one");
            case 2 -> doSomething("two");
            default -> doSomething("default");
        }
    }
}
