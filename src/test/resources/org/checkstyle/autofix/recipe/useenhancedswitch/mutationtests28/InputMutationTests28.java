/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.useenhancedswitch.mutationtests28;

public class InputMutationTests28 {

    enum Season { SPRING, SUMMER, FALL, WINTER; int id = 0; }

    void exhaustiveNoDefaultAssignment(Season s) {
        int val = 0;
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (s) {
            case SPRING: val = 1; break;
            case SUMMER: val = 2; break;
            case FALL: val = 3; break;
            case WINTER: val = 4; break;
        }
        System.out.println(val);
    }

    void assignmentWithBlock(int x) {
        int val;
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1: {
                System.out.println("block");
                val = 1;
                break;
            }
            default:
                val = 0;
        }
        System.out.println(val);
    }

    void assignmentWithSingleStatementBlock(int x) {
        int val;
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1: {
                val = 1;
                break;
            }
            default:
                val = 0;
        }
        System.out.println(val);
    }

    enum EmptyEnum {}

    void emptyEnumExhaustive(EmptyEnum e) {
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (e) {
            default:
                System.out.println("default");
        }
    }

    void mixedLabelsAssignment(Season s) {
        int val;
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (s) {
            case SPRING:
            case SUMMER:
                val = 1;
                break;
            default:
                val = 0;
        }
        System.out.println(val);
    }
}
