/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.useenhancedswitch.mutationtests3;

public class InputMutationTests3 {

    enum Day { MON, TUE, WED }

    int enumExhaustive(Day d) {
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (d) {
            case MON: return 1;
            case TUE: return 2;
            case WED: return 3;
            default: return 0;
        }
    }

    void assignmentWithInitializer(int x) {
        String s = "initial";
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1:
                s = "one";
                break;
            default:
                s = "default";
                break;
        }
        System.out.println(s);
    }

    void assignmentWithoutInitializer(int x) {
        String s;
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1:
                s = "one";
                break;
            default:
                s = "default";
                break;
        }
        System.out.println(s);
    }

    void trailingComment(int x) {
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1:
                System.out.println("one");
                break; // trailing comment
            default:
                System.out.println("default");
                break;
        }
    }

    int yieldInBlock(int x) {
        // violation below, 'Switch can be replaced with enhanced switch'
        return switch (x) {
            case 1:
                {
                    System.out.println("one");
                    yield 1;
                }
            default:
                yield 0;
        };
    }

    void nestedSwitches(int x, int y) {
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1:
                // violation below, 'Switch can be replaced with enhanced switch'
                switch (y) {
                    case 1: break;
                    default: break;
                }
                break;
            default: break;
        }
    }
}
