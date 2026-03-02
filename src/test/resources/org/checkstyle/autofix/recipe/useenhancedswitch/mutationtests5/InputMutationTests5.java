/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.useenhancedswitch.mutationtests5;

public class InputMutationTests5 {
    void testAssignmentSwitch(int e) {
        int x;
        // violation below, 'Switch can be replaced with enhanced switch'
        switch(e) {
            case 1:
                System.out.println();
                x = 5;
                break;
            case 2:
                x = 6;
                break;
            default:
                x = 7;
                break;
        }

        int y;
        switch(e) {
            case 1 -> {
                System.out.println();
                y = 5;
            }
            case 2 -> y = 6;
            default -> y = 7;
        }
    }
}
