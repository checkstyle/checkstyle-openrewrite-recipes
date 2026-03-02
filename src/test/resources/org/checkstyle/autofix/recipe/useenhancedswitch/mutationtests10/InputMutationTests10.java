/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.useenhancedswitch.mutationtests10;

public class InputMutationTests10 {

    enum Direction { UP, DOWN, LEFT, RIGHT }

    public void testEmptyAssignment() {
        int x = 0;
        int i = 1;
        // violation below, 'Switch can be replaced with enhanced switch'
        switch(i) {
            case 1:
            case 2:
                x = 1;
                break;
            case 3:
                // Nothing happening
                break;
            default:
                x = 2;
                break;
        }
    }

    public int testEmptyReturn() {
        int i = 1;
        // violation below, 'Switch can be replaced with enhanced switch'
        switch(i) {
            case 1:
            case 2:
                return 1;
            case 3:
                // Nothing here
                break;
            default:
                return 2;
        }
        return 0;
    }

    public void testBreakInBlock() {
        int x = 0;
        int i = 1;
        // violation below, 'Switch can be replaced with enhanced switch'
        switch(i) {
            case 1: {
                if (true) break;
                x = 1;
                break;
            }
            default:
                x = 2;
                break;
        }
    }

    public void testInvalidAssignment() {
        int x = 0;
        int y = 0;
        int i = 1;
        // violation below, 'Switch can be replaced with enhanced switch'
        switch(i) {
            case 1:
                x = 1;
                break;
            case 2:
                y = 1;
                break;
            default:
                x = 2;
                break;
        }
    }

    public int testEnumExhaustiveMissing() {
        Direction d = Direction.UP;
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (d) {
            case UP:
                return 1;
            case DOWN:
            case LEFT:
                return 2;
        }
        return 0;
    }

    public int testEnumExhaustiveComplete() {
        Direction d = Direction.UP;
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (d) {
            case UP:
                return 1;
            case DOWN:
                return 2;
            case LEFT:
                return 3;
            case RIGHT:
                return 4;
            default:
                return 0;
        }
    }
}
