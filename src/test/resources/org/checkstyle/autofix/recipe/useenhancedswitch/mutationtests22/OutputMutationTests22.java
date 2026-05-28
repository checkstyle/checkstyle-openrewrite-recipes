/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.useenhancedswitch.mutationtests22;

public class OutputMutationTests22 {
    void testNegativeControlFlow(int x) {
        int y;
        switch (x) {
            case 1 -> {
                if (x > 0) return;
                y = 1;}
            default -> y = 0;
        }
    }

    void testNegativeAssignment(int x) {
        int[] arr = new int[1];
        switch (x) {
            case 1 -> arr[0] = 1;
            default -> arr[0] = 0;
        }
    }

    enum SmallEnum { A, B }
    void testNegativeEnum(SmallEnum e) {
        int x;
        switch (e) {
            case A -> x = 1;
        }
    }

    void testNegativeAssignmentNoAssign(int x) {
        int y;
        switch (x) {
            case 1 -> System.out.println(1);
            default -> y = 0;
        }
    }

    int testNegativeReturnNoDefault(SmallEnum e) {
        switch (e) {
            case A -> { return 1;}
        }
        return 0;
    }

    int testNegativeReturnControlFlow(int x) {
        switch (x) {
            case 1 -> {
                if (x > 0) {
                    for (int i=0; i<10; i++) {
                        if (i == 5) break;
                    }
                }
                return 1;}
            default -> { return 0;
            }
        }
    }

    void testAssignmentFallthrough(int x) {
        int y = switch (x) {
            case 1, 2 -> 1;
            default -> 0;
        };
    }

    void testAssignmentBlock(int x) {
        int y = switch (x) {
            case 1 -> {
                System.out.println(1);
                yield 1;
            }
            default -> 0;
        };
    }

    void testNegativeAssignmentNonIdentifier(int x) {
        int y;
        switch (x) {
            case 1 -> this.y = 1;
            default -> y = 0;
        }
    }

    private int y;
}
