/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.useenhancedswitch.mutationtests12;

public class OutputMutationTests12 {

    enum Color { RED, GREEN, BLUE }

    public int testFallthroughAssignment(int i) {
        int x = 0;
        switch (i) {
            case 1, 2 -> x = 1;
            default -> x = 2;
        }
        return x;
    }

    public int testFallthroughReturn(int i) {
        return switch (i) {
            case 1, 2 -> 1;
            default -> 2;
        };
    }

    public void testNonIdentifierAssignment(int i) {
        this.field = 0;
        switch (i) {
            case 1 -> this.field = 1;
            default -> this.field = 2;
        }
    }

    private int field;

    public int testInexhaustiveEnum(Color c) {
        switch (c) {
            case RED -> {
                return 1;
            }
            case GREEN -> {
                return 2;
            }
        }
        return 0;
    }

    public void testNoSpaceIndent(int i) {
switch(i) {
case 1 -> System.out.println("1");
default -> System.out.println("2");
}
    }
}
