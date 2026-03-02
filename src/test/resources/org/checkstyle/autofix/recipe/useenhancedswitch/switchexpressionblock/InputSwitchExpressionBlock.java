/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.useenhancedswitch.switchexpressionblock;

public class InputSwitchExpressionBlock {

    void doSomething(String param) {
    }

    int multiStatementYield(int x) {
        // violation below, 'Switch can be replaced with enhanced switch'
        int result = switch (x) {
            case 1:
                doSomething("processing");
                yield 10;
            case 2:
            case 3:
                yield 20;
            default:
                yield 0;
        };
        return result;
    }
}
