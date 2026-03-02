/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.useenhancedswitch.mutationtests13;

public class OutputMutationTests13 {

    enum Color { RED, GREEN, BLUE }
    enum Direction { NORTH, SOUTH }

    void differentVariableAssignment(int i) {
        int x = 0;
        int y = 0;
        switch (i) {
            case 1 -> x = 1;
            default -> y = 2;
        }
    }

    void emptyCaseInAssignmentSwitch(int i) {
        int x = 0;
        switch (i) {
            case 1 -> {}
            default -> x = 2;
        }
    }

    int inexhaustiveEnumDifferentType(Color c) {
        switch (c) {
            case RED -> {
                return 1;
            }
            case GREEN -> {
                return 2;
            }
        }
        return 0;
    }

    void switchAtEndOfBlock() {
        int x;
    }
}
