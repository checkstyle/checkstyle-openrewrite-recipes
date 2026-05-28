/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.useenhancedswitch.mutationtests15;

public class InputMutationTests15 {
    enum Color { RED, GREEN, BLUE }
    enum SmallEnum { A, B }

    int testThrowUnreachable(int x) {
        switch (x) { // violation 'Switch can be replaced with enhanced switch.'
            case 1:
                throw new RuntimeException("err");
            default:
                return 0;
        }
    }

    void testAdjustNoAssignment(int x) {
        switch (x) { // violation 'Switch can be replaced with enhanced switch.'
            case 1:
                System.out.println("one");
                break;
            case 2:
                System.out.println("two");
                break;
            default:
                System.out.println("default");
                break;
        }
    }

    void testAdjustNonIdentifierAssignment(int x) {
        int[] arr = new int[2];
        switch (x) { // violation 'Switch can be replaced with enhanced switch.'
            case 1:
                arr[0] = 1;
                break;
            default:
                arr[0] = 0;
                break;
        }
    }

    int testReturnWithThrowCase(int x) {
        switch (x) { // violation 'Switch can be replaced with enhanced switch.'
            case 1:
                return 10;
            case 2:
                return 20;
            default:
                throw new IllegalArgumentException("bad");
        }
    }

    int testReturnExpression(int x) {
        switch (x) { // violation 'Switch can be replaced with enhanced switch.'
            case 1:
                return x + 1;
            case 2:
                return x + 2;
            default:
                return 0;
        }
    }

    void testPartialEnum(SmallEnum e) {
        switch (e) { // violation 'Switch can be replaced with enhanced switch.'
            case A:
                System.out.println("A");
                break;
        }
    }

    int testFullyExhaustiveReturn(Color c) {
        switch (c) { // violation 'Switch can be replaced with enhanced switch.'
            case RED:
                return 1;
            case GREEN:
                return 2;
            case BLUE:
                return 3;
            default:
                throw new IllegalStateException("impossible");
        }
    }

    void testBlockEndSpace(int x) {
        switch (x) { // violation 'Switch can be replaced with enhanced switch.'
            case 1: {
                System.out.println("a");
                System.out.println("b");
                break;
            }
            default: {
                System.out.println("c");
                break;
            }
        }
    }
}
