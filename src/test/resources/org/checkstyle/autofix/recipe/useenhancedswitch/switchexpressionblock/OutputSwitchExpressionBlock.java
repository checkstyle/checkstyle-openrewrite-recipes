/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.useenhancedswitch.switchexpressionblock;

public class OutputSwitchExpressionBlock {

    void doSomething(String param) {
    }

    int multiStatementYield(int x) {
        int result = switch (x) {
            case 1 -> {
                doSomething("processing");
                yield 10;
            }
            case 2, 3 -> 20;
            default -> 0;
        };
        return result;
    }
}
