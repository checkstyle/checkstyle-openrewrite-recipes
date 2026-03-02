/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.useenhancedswitch.mutationtests17;

public class OutputMutationTests17 {
    public void test(int x) {
        int y = switch (x) {
            case 1, 2 -> 10;
            default -> 0;
        };
    }
}
