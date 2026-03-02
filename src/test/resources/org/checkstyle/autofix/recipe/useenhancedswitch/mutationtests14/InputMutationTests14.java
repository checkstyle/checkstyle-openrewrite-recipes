/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.useenhancedswitch.mutationtests14;

public class InputMutationTests14 {
    enum Color { RED, GREEN, BLUE }

    void test(int x) {
        int y;
        switch (x) { // violation 'Switch can be replaced with enhanced switch.'
            case 1:
            case 2:
                y = 1;
                break;
            case 3:
                y = 2;
                x = 3;
                break;
            default:
                y = 0;
                break;
        }
    }

    void testEnum(Color c) {
        int x;
        switch (c) { // violation 'Switch can be replaced with enhanced switch.'
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

    void testIndent(int x) {
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

    int testReturn(int x) {
        switch (x) { // violation 'Switch can be replaced with enhanced switch.'
            case 1:
                return 1;
            default:
                return 0;
        }
    }

    void testIndent2(int x) {
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

    void testIndent3(int x) {
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

    void testNoNewline(int x) {
        switch (x) { // violation 'Switch can be replaced with enhanced switch.'
            case 1: x = 1; break;
            default: break;
        }
    }

    void testIndentVisitorNoNewline(int x) {
        int y;
        switch (x) { // violation 'Switch can be replaced with enhanced switch.'
            case 1:
                y = 1; x = 2;
                break;
            default:
                y = 0;
                break;
        }
    }

    void testDifferentVariableAssignment(int x) {
        int y;
        int z;
        switch (x) { // violation 'Switch can be replaced with enhanced switch.'
            case 1:
                y = 1;
                break;
            case 2:
                z = 1;
                break;
            default:
                y = 0;
                break;
        }
    }

    void testEmptyCase(int x) {
        int y;
        switch (x) { // violation 'Switch can be replaced with enhanced switch.'
            case 1:
                y = 1;
                break;
            case 2:
                break;
            default:
                y = 0;
                break;
        }
    }

    enum OtherColor { RED, GREEN }
    void testMultipleEnums(Color c, OtherColor oc) {
        int x;
        switch (c) { // violation 'Switch can be replaced with enhanced switch.'
            case RED: x = 1; break;
            case GREEN: x = 2; break;
            case BLUE: x = 3; break;
        }
    }

    void testContinueInCase(int x) {
        for (int i = 0; i < 10; i++) {
            switch (x) { // violation 'Switch can be replaced with enhanced switch.'
                case 1:
                    if (x > 0) {
                        continue;
                    }
                    System.out.println("one");
                    break;
                case 2:
                    System.out.println("two");
                    break;
                default:
                    break;
            }
        }
    }

    void testLabeledBreakInCase(int x) {
        outer:
        for (int i = 0; i < 10; i++) {
            switch (x) { // violation 'Switch can be replaced with enhanced switch.'
                case 1:
                    if (x > 0) {
                        break outer;
                    }
                    System.out.println("one");
                    break;
                case 2:
                    System.out.println("two");
                    break;
                default:
                    break;
            }
        }
    }

    void testThrowInAssignment(int x) {
        int y;
        switch (x) { // violation 'Switch can be replaced with enhanced switch.'
            case 1:
                y = 1;
                break;
            default:
                throw new RuntimeException("bad");
        }
    }

    void testNoNewlineIndent(int x) {
        int y;
        switch (x) { // violation 'Switch can be replaced with enhanced switch.'
            case 1: y = 1; break;
            default: y = 0; break;
        }
    }

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

