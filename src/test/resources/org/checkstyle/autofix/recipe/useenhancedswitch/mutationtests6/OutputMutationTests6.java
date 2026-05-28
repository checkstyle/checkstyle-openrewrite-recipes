/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.useenhancedswitch.mutationtests6;

public class OutputMutationTests6 {

    enum Season { SPRING, SUMMER, FALL, WINTER; int id = 0; }

    void assignmentWrongVar(int x) {
        int val = 0;
        int other = 0;
        switch (x) {
            case 1 -> other = 99;
            default -> other = 0;
        }
        System.out.println(val);
    }

    void assignmentWithAllThrows(int x) {
        int val = switch (x) {
            case 1 -> 10;
            default -> throw new RuntimeException("err");
        };
        System.out.println(val);
    }

    void blockAssignment(int x) {
        int val = switch (x) {
            case 1 -> {
                System.out.println("processing");
                System.out.println("more");
                yield 42;
            }
            case 2 -> 99;
            default -> throw new IllegalStateException("bad");
        };
        System.out.println(val);
    }

    void partialEnum(Season s) {
        switch (s) {
            case SPRING -> System.out.println("spring");
            case SUMMER -> System.out.println("summer");
            default -> System.out.println("other");
        }
    }

    int exhaustiveEnumReturn(Season s) {
        return switch (s) {
            case SPRING -> 1;
            case SUMMER -> 2;
            case FALL -> 3;
            case WINTER -> 4;
        };
    }

    int singleReturnExpr(int x) {
        return switch (x) {
            case 1 -> 10;
            case 2 -> 20;
            default -> 0;
        };
    }

    int singleThrowExpr(int x) {
        return switch (x) {
            case 1 -> 10;
            default -> throw new IllegalArgumentException("nope");
        };
    }
}
