/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.useenhancedswitch.mutationtests10;

public class OutputMutationTests10 {

    enum Direction { UP, DOWN, LEFT, RIGHT }

    public void testEmptyAssignment() {
        int x = 0;
        int i = 1;
        switch(i) {
            case 1, 2 -> x = 1;
            case 3 -> {}
            default -> x = 2;
        }
    }

    public int testEmptyReturn() {
        int i = 1;
        switch(i) {
            case 1, 2 -> {
                return 1;
            }
            case 3 -> {}
            default -> {
                return 2;
            }
        }
        return 0;
    }

    public void testBreakInBlock() {
        int x = 0;
        int i = 1;
        switch(i) {
            case 1 -> {
                if (true) break;
                x = 1;
            }
            default -> x = 2;
        }
    }

    public void testInvalidAssignment() {
        int x = 0;
        int y = 0;
        int i = 1;
        switch(i) {
            case 1 -> x = 1;
            case 2 -> y = 1;
            default -> x = 2;
        }
    }

    public int testEnumExhaustiveMissing() {
        Direction d = Direction.UP;
        switch (d) {
            case UP -> {
                return 1;
            }
            case DOWN, LEFT -> {
                return 2;
            }
        }
        return 0;
    }

    public int testEnumExhaustiveComplete() {
        Direction d = Direction.UP;
        return switch (d) {
            case UP -> 1;
            case DOWN -> 2;
            case LEFT -> 3;
            case RIGHT -> 4;
        };
    }
}
