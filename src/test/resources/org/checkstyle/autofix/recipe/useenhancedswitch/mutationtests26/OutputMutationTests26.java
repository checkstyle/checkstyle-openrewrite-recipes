/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.useenhancedswitch.mutationtests26;

public class OutputMutationTests26 {

    void assignmentArrowBlock(int x) {
        int val = switch (x) {
            case 1 -> 10;
            case 2 -> {
                System.out.println("two");
                yield 20;
            }
            default -> 0;
        };
        System.out.println(val);
    }

    int blockReturnExpr(int x) {
        return switch (x) {
            case 1 -> {
                System.out.println("case1");
                yield 1;
            }
            case 2 -> 2;
            default -> {
                System.out.println("default");
                yield 0;
            }
        };
    }

    void assignmentWithThrow(int x) {
        String val = switch (x) {
            case 1 -> "one";
            default -> throw new RuntimeException("error");
        };
        System.out.println(val);
    }

    void multiStatement(int x) {
        switch (x) {
            case 1 -> {
                System.out.println("a");
                System.out.println("b");
            }
            default -> System.out.println("default");
        }
    }
}
