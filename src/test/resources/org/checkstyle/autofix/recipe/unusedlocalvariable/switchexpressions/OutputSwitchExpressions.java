/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.switchexpressions;

public class OutputSwitchExpressions {

    public void testSwitchExpression(int x) {
        int y = switch (x) {
            case 1 -> {
                int a = 12;
                yield a;
            }
            default -> 0;
        };
        System.out.println(y);
    }

    public void testSwitchStatement(int x) {
        switch (x) {
            case 1:
                break;
            default:
                break;
        }
    }
}
