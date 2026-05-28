/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.useenhancedswitch.mutationtests4;

public class InputMutationTests4 {

    enum Color { RED, GREEN, BLUE }

    void enumExhaustiveNoDefault(Color color) {
        int x = 0;
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (color) {
            case RED: x = 1; break;
            case GREEN: x = 2; break;
            case BLUE: x = 3; break;
        }
        System.out.println(x);
    }

    void assignmentBlockMultiStmt(Color color) {
        int val;
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (color) {
            case RED:
                System.out.println("red");
                val = 1;
                break;
            case GREEN:
                System.out.println("green");
                val = 2;
                break;
            default:
                val = 0;
                break;
        }
        System.out.println(val);
    }

    void assignmentFallthrough(int x) {
        String val;
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1:
            case 2:
                val = "one or two";
                break;
            case 3:
                val = "three";
                break;
            default:
                val = "other";
                break;
        }
        System.out.println(val);
    }

    int returnWithThrow(int x) {
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1: return 10;
            case 2: return 20;
            default: throw new IllegalArgumentException("bad");
        }
    }

    void blockSingleStmt(int x) {
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1: {
                System.out.println("one");
                break;
            }
            default: {
                System.out.println("default");
                break;
            }
        }
    }

    int returnBlockMultiStmt(int x) {
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1: {
                System.out.println("processing");
                return 100;
            }
            default: {
                System.out.println("default processing");
                return 0;
            }
        }
    }
}
