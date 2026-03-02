/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.useenhancedswitch.mutationtests18;

public class InputMutationTests18 {

    int testReturnSwitchExpressionDeadCode(int x) {
        int y;
        switch (x) { // violation 'Switch can be replaced with enhanced switch.'
            case 1:
                y = 10;
                break;
            default:
                y = 0;
                break;
        }
        return y;
    }

    void testBreakRemoval(int x) {
        switch (x) { // violation 'Switch can be replaced with enhanced switch.'
            case 1: {
                System.out.println("one");
                break;
            }
            default:
                System.out.println("other");
                break;
        }
    }

    void testBlockEndSpaceSubstring(int x) {
        switch (x) { // violation 'Switch can be replaced with enhanced switch.'
            case 1:
                System.out.println("a");
                System.out.println("b");
                break;
            default:
                break;
        }
    }

    void testFallthroughControlFlow(int x) {
        switch (x) { // violation 'Switch can be replaced with enhanced switch.'
            case 1:
            case 2:
                System.out.println("one or two");
                break;
            case 3:
                System.out.println("three");
                break;
            default:
                break;
        }
    }
}
