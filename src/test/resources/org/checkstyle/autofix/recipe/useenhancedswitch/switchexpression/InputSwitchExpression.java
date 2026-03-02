/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.useenhancedswitch.switchexpression;

public class InputSwitchExpression {

    int switchExpressions(int x) {
        // violation below, 'Switch can be replaced with enhanced switch'
        int y = switch (x) {
            case 1 : yield 1;
            case 2 : yield 2;
            case 3 : yield 3;
            default: yield 0;
        };
        return y;
    }
}
