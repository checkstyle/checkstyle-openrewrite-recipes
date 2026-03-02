/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.useenhancedswitch.mutationtests8;

public class OutputMutationTests8 {

    enum Day { MON, TUE, WED }

    void missingDefaultAssignment(int x) {
        String s;
        switch (x) {
            case 1 -> s = "one";
        }
    }

    int nonExhaustiveEnumReturn(Day d) {
        switch (d) {
            case MON -> { return 1;
            }
            case TUE -> { return 2;
            }
        }
        return 0;
    }

    void multipleVariables(int x) {
        int a, b;
        switch (x) {
            case 1 -> { a = 1; b = 2;
            }
            default -> { a = 0; b = 0;
            }
        }
    }

    void initializedVariable(int x) {
        int a = 0;
        switch (x) {
            case 1 -> a = 1;
            default -> a = 2;
        }
    }

    int nestedReturnSwitch(int x, int y) {
        return switch (x) {
            case 1 -> switch (y) {
                    case 1 -> 1;
                    default -> 0;
                };
            default -> -1;
        };
    }
}
