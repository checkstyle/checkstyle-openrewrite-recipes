/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.useenhancedswitch.mutationtests16;

public class OutputMutationTests16 {

    void testDifferentIdentifiers(int x) {
        int y;
        int z;
        switch (x) {
            case 1 -> y = 1;
            case 2 -> z = 2;
            default -> y = 0;
        }
    }

    int testPartialReturnDrop(int x) {
        switch (x) {
            case 1 -> {
                return 1;}
            case 2 -> System.out.println("Wait!");
            default -> {
                return 0;
            }
        }
        return -1;
    }

    void testMeaningfulStatementsBreakLabel(int x) {
        outer:
        switch (x) {
            case 1 -> {
                System.out.println(1);
                break outer;}
            default -> System.out.println(0);
        }
    }

    int testIntSwitchNoDefault(int x) {
        switch (x) {
            case 1 -> {
                return 1;}
            case 2 -> {
                return 2;
            }
        }
        return 0;
    }

    void testCollectHandledLiteral(String str) {
        switch (str) {
            case "A" -> System.out.println("A");
            case "B" -> System.out.println("B");
            default -> {}
        }
    }

    void testBlockEndSpacePaddingEdge(int x) {
        switch (x) {
            case 1 -> {
                int y = 1; /* pad */ }
            default -> System.out.println(0);
        }
    }

    void testAdjustSpaceEdge(int x) {
        switch (x) {
            case 1 -> { int y = 1; }
            default -> {}
        }
    }
}
