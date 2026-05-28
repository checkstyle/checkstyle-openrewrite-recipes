/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.useenhancedswitch.mutationtests27;

public class InputMutationTests27 {

    enum Season { SPRING, SUMMER, FALL, WINTER; int id = 0; }

    void multipleVarDecls(int x) {
        int a;
        int b;
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1:
                a = 1;
                break;
            default:
                a = 0;
        }
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1:
                b = 1;
                break;
            default:
                b = 0;
        }
        System.out.println(a + b);
    }

    int switchExprTraditional(int x) {
        // violation below, 'Switch can be replaced with enhanced switch'
        return switch (x) {
            case 1: yield 10;
            case 2: yield 20;
            default: yield 0;
        };
    }

    void noAssignmentConversion(int x) {
        String s = "initial";
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1:
                s = "one";
                break;
            default:
                s = "default";
        }
        System.out.println(s);
    }

    void enumAssignment(Season s) {
        int val;
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (s) {
            case SPRING:
                val = 1;
                break;
            case SUMMER:
                val = 2;
                break;
            case FALL:
                val = 3;
                break;
            case WINTER:
                val = 4;
                break;
            default:
                val = 0;
                break;
        }
        System.out.println(val);
    }

    void singleBlockCase(int x) {
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1: {
                System.out.println("block");
                break;
            }
            default:
                System.out.println("default");
        }
    }

    void multipleVarInOneDecl(int x) {
        int a, b;
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1:
                a = 1;
                break;
            default:
                a = 0;
        }
        System.out.println(a);
    }
}
