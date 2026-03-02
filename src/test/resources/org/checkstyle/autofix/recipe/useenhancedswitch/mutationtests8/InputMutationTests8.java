/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.useenhancedswitch.mutationtests8;

public class InputMutationTests8 {

    enum Day { MON, TUE, WED }

    void missingDefaultAssignment(int x) {
        String s;
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1:
                s = "one";
                break;
        }
    }

    int nonExhaustiveEnumReturn(Day d) {
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (d) {
            case MON: return 1;
            case TUE: return 2;
        }
        return 0;
    }

    void multipleVariables(int x) {
        int a, b;
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1: a = 1; b = 2; break;
            default: a = 0; b = 0; break;
        }
    }

    void initializedVariable(int x) {
        int a = 0;
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1: a = 1; break;
            default: a = 2; break;
        }
    }

    int nestedReturnSwitch(int x, int y) {
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1:
                // violation below, 'Switch can be replaced with enhanced switch'
                switch (y) {
                    case 1: return 1;
                    default: return 0;
                }
            default:
                return -1;
        }
    }
}
