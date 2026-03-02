/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.useenhancedswitch.mutationtests18;

public class OutputMutationTests18 {

    int testReturnSwitchExpressionDeadCode(int x) {
        int y = switch (x) {
            case 1 -> 10;
            default -> 0;
        };
        return y;
    }

    void testBreakRemoval(int x) {
        switch (x) {
            case 1 -> System.out.println("one");
            default -> System.out.println("other");
        }
    }

    void testBlockEndSpaceSubstring(int x) {
        switch (x) {
            case 1 -> {
                System.out.println("a");
                System.out.println("b");}
            default -> {}
        }
    }

    void testFallthroughControlFlow(int x) {
        switch (x) {
            case 1, 2 -> System.out.println("one or two");
            case 3 -> System.out.println("three");
            default -> {}
        }
    }
}
