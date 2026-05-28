/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.useenhancedswitch.mutationtests23;

public class InputMutationTests23 {
    enum Color { RED, GREEN, BLUE }
    enum SmallEnum { A, B }

    void testExhaustiveEnumNoDefault(Color e) {
        int x;
        switch (e) { // violation 'Switch can be replaced with enhanced switch.'
            case RED:
                x = 1;
                break;
            case GREEN:
                x = 2;
                break;
            case BLUE:
                x = 3;
                break;
        }
    }

    void testIndentNoNewline(int x) {
        int y;
        switch (x) { // violation 'Switch can be replaced with enhanced switch.'
            case 1:
                y = 1;
                break;
            default:
                y = 0;
                break;
        }
    }

    int testExhaustiveWithDefault(Color e) {
        switch (e) { // violation 'Switch can be replaced with enhanced switch.'
            case RED:
                return 1;
            case GREEN:
                return 2;
            case BLUE:
                return 3;
            default:
                return 0;
        }
    }

    int testNonExhaustiveEnumNoDefault(SmallEnum e) {
        switch (e) { // violation 'Switch can be replaced with enhanced switch.'
            case A:
                return 1;
        }
        return 0;
    }

    int testExhaustiveEnumReturnNoDefault(Color e) {
        switch (e) { // violation 'Switch can be replaced with enhanced switch.'
            case RED:
                return 1;
            case GREEN:
                return 2;
            case BLUE:
                return 3;
        }
        return 0;
    }

    void testEnumDefaultOnly(Color e) {
        int x;
        switch (e) { // violation 'Switch can be replaced with enhanced switch.'
            default:
                x = 1;
                break;
        }
    }

    void testContinueInSwitch() {
        for (int i = 0; i < 5; i++) {
            switch (i) { // violation 'Switch can be replaced with enhanced switch.'
                case 1:
                    continue;
                default:
                    int x = i;
                    break;
            }
        }
    }
}
