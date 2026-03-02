/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.useenhancedswitch.mutationtests5;

public class OutputMutationTests5 {
    void testAssignmentSwitch(int e) {
        int x = switch(e) {
            case 1 -> {
                System.out.println();
                yield 5;
            }
            case 2 -> 6;
            default -> 7;
        };

        int y = switch(e) {
            case 1 -> {
                System.out.println();
                yield 5;
            }
            case 2 -> 6;
            default -> 7;
        };
    }
}
