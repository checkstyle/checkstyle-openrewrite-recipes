/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.useenhancedswitch.mutationtests13;

public class InputMutationTests13 {

    enum Color { RED, GREEN, BLUE }
    enum Direction { NORTH, SOUTH }

    void differentVariableAssignment(int i) {
        int x = 0;
        int y = 0;
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (i) {
            case 1:
                x = 1;
                break;
            default:
                y = 2;
                break;
        }
    }

    void emptyCaseInAssignmentSwitch(int i) {
        int x = 0;
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (i) {
            case 1:
                break;
            default:
                x = 2;
                break;
        }
    }

    int inexhaustiveEnumDifferentType(Color c) {
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (c) {
            case RED:
                return 1;
            case GREEN:
                return 2;
        }
        return 0;
    }

    void switchAtEndOfBlock() {
        int x;
    }
}
