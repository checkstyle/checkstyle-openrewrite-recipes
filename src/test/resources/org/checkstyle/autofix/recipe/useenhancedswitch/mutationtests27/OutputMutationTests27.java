/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.useenhancedswitch.mutationtests27;

public class OutputMutationTests27 {

    enum Season { SPRING, SUMMER, FALL, WINTER; int id = 0; }

    void multipleVarDecls(int x) {
        int a;
        int b;
        switch (x) {
            case 1 -> a = 1;
            default -> a = 0;
        }
        switch (x) {
            case 1 -> b = 1;
            default -> b = 0;
        }
        System.out.println(a + b);
    }

    int switchExprTraditional(int x) {
        return switch (x) {
            case 1 -> 10;
            case 2 -> 20;
            default -> 0;
        };
    }

    void noAssignmentConversion(int x) {
        String s = "initial";
        switch (x) {
            case 1 -> s = "one";
            default -> s = "default";
        }
        System.out.println(s);
    }

    void enumAssignment(Season s) {
        int val = switch (s) {
            case SPRING -> 1;
            case SUMMER -> 2;
            case FALL -> 3;
            case WINTER -> 4;
            default -> 0;
        };
        System.out.println(val);
    }

    void singleBlockCase(int x) {
        switch (x) {
            case 1 -> System.out.println("block");
            default -> System.out.println("default");
        }
    }

    void multipleVarInOneDecl(int x) {
        int a, b;
        switch (x) {
            case 1 -> a = 1;
            default -> a = 0;
        }
        System.out.println(a);
    }
}
