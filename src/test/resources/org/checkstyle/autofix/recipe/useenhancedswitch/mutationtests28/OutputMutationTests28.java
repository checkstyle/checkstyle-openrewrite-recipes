/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.useenhancedswitch.mutationtests28;

public class OutputMutationTests28 {

    enum Season { SPRING, SUMMER, FALL, WINTER; int id = 0; }

    void exhaustiveNoDefaultAssignment(Season s) {
        int val = 0;
        switch (s) {
            case SPRING -> val = 1;
            case SUMMER -> val = 2;
            case FALL -> val = 3;
            case WINTER -> val = 4;
        }
        System.out.println(val);
    }

    void assignmentWithBlock(int x) {
        int val = switch (x) {
            case 1 -> {
                System.out.println("block");
                yield 1;
            }
            default -> 0;
        };
        System.out.println(val);
    }

    void assignmentWithSingleStatementBlock(int x) {
        int val = switch (x) {
            case 1 -> 1;
            default -> 0;
        };
        System.out.println(val);
    }

    enum EmptyEnum {}

    void emptyEnumExhaustive(EmptyEnum e) {
        switch (e) {
            default -> System.out.println("default");
        }
    }

    void mixedLabelsAssignment(Season s) {
        int val = switch (s) {
            case SPRING, SUMMER -> 1;
            default -> 0;
        };
        System.out.println(val);
    }
}
