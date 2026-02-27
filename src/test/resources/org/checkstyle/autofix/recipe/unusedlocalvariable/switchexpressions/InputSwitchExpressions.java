/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.switchexpressions;

public class InputSwitchExpressions {

    public void testSwitchExpression(int x) {
        int y = switch (x) {
            case 1 -> {
                int a = 12;
                yield a;
            }
            default -> 0;
        };
        System.out.println(y);

        int z = 1; // violation 'Unused named local variable'
        z = switch (x) {
            case 1 -> 2;
            default -> 3;
        };
    }

    public void testSwitchStatement(int x) {
        switch (x) {
            case 1:
                int a = 12; // violation 'Unused named local variable'
                a = 13;
                break;
            default:
                break;
        }
    }
}
