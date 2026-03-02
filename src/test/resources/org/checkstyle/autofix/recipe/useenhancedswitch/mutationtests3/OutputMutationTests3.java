/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.useenhancedswitch.mutationtests3;

public class OutputMutationTests3 {

    enum Day { MON, TUE, WED }

    int enumExhaustive(Day d) {
        return switch (d) {
            case MON -> 1;
            case TUE -> 2;
            case WED -> 3;
        };
    }

    void assignmentWithInitializer(int x) {
        String s = "initial";
        switch (x) {
            case 1 -> s = "one";
            default -> s = "default";
        }
        System.out.println(s);
    }

    void assignmentWithoutInitializer(int x) {
        String s = switch (x) {
            case 1 -> "one";
            default -> "default";
        };
        System.out.println(s);
    }

    void trailingComment(int x) {
        switch (x) {
            case 1 -> System.out.println("one"); // trailing comment
            default -> System.out.println("default");
        }
    }

    int yieldInBlock(int x) {
        return switch (x) {
            case 1 -> {
                System.out.println("one");
                yield 1;
            }
            default -> 0;
        };
    }

    void nestedSwitches(int x, int y) {
        switch (x) {
            case 1 -> {
                switch (y) {
                    case 1 -> {}
                    default -> {}
                }
            }
            default -> {}
        }
    }
}
