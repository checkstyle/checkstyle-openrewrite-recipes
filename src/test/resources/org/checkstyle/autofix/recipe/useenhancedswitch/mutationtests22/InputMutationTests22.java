/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.useenhancedswitch.mutationtests22;

public class InputMutationTests22 {
    void testNegativeControlFlow(int x) {
        int y;
        switch (x) { // violation 'Switch can be replaced with enhanced switch.'
            case 1:
                if (x > 0) return;
                y = 1;
                break;
            default:
                y = 0;
                break;
        }
    }

    void testNegativeAssignment(int x) {
        int[] arr = new int[1];
        switch (x) { // violation 'Switch can be replaced with enhanced switch.'
            case 1:
                arr[0] = 1;
                break;
            default:
                arr[0] = 0;
                break;
        }
    }

    enum SmallEnum { A, B }
    void testNegativeEnum(SmallEnum e) {
        int x;
        switch (e) { // violation 'Switch can be replaced with enhanced switch.'
            case A: x = 1; break;
        }
    }

    void testNegativeAssignmentNoAssign(int x) {
        int y;
        switch (x) { // violation 'Switch can be replaced with enhanced switch.'
            case 1: System.out.println(1); break;
            default: y = 0; break;
        }
    }

    int testNegativeReturnNoDefault(SmallEnum e) {
        switch (e) { // violation 'Switch can be replaced with enhanced switch.'
            case A: return 1;
        }
        return 0;
    }

    int testNegativeReturnControlFlow(int x) {
        switch (x) { // violation 'Switch can be replaced with enhanced switch.'
            case 1:
                if (x > 0) {
                    for (int i=0; i<10; i++) {
                        if (i == 5) break;
                    }
                }
                return 1;
            default: return 0;
        }
    }

    void testAssignmentFallthrough(int x) {
        int y;
        switch (x) { // violation 'Switch can be replaced with enhanced switch.'
            case 1:
            case 2: y = 1; break;
            default: y = 0; break;
        }
    }

    void testAssignmentBlock(int x) {
        int y;
        switch (x) { // violation 'Switch can be replaced with enhanced switch.'
            case 1: {
                System.out.println(1);
                y = 1;
                break;
            }
            default: y = 0; break;
        }
    }

    void testNegativeAssignmentNonIdentifier(int x) {
        int y;
        switch (x) { // violation 'Switch can be replaced with enhanced switch.'
            case 1:
                this.y = 1; // Not y = 1;
                break;
            default:
                y = 0;
                break;
        }
    }

    private int y;
}
