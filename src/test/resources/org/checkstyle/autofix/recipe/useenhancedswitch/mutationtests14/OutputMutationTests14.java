/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.useenhancedswitch.mutationtests14;

public class OutputMutationTests14 {
    enum Color { RED, GREEN, BLUE }

    void test(int x) {
        int y;
        switch (x) {
            case 1, 2 -> y = 1;
            case 3 -> {
                y = 2;
                x = 3;
            }
            default -> y = 0;
        }
    }

    void testEnum(Color c) {
        int x = switch (c) {
            case RED -> 1;
            case GREEN -> 2;
            case BLUE -> 3;
        };
    }

    void testIndent(int x) {
        int y = switch (x) {
            case 1 -> 1;
            default -> 0;
        };
    }

    int testReturn(int x) {
        return switch (x) {
            case 1 -> 1;
            default -> 0;
        };
    }

    void testIndent2(int x) {
        int y = switch (x) {
            case 1 -> 1;
            default -> 0;
        };
    }

    void testIndent3(int x) {
        int y = switch (x) {
            case 1 -> 1;
            default -> 0;
        };
    }

    void testNoNewline(int x) {
        switch (x) {
            case 1 -> x = 1;
            default -> {}
        }
    }

    void testIndentVisitorNoNewline(int x) {
        int y;
        switch (x) {
            case 1 -> {
                y = 1; x = 2;}
            default -> y = 0;
        }
    }
}
