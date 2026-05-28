/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.useenhancedswitch.mutationtests25;

public class OutputMutationTests25 {
    enum Color { RED, GREEN, BLUE }

    int testCollectHandled(Color c) {
        return switch (c) {
            case RED, GREEN -> 1;
            case BLUE -> 2;
        };
    }

    void testEmptyDefault(int x) {
        switch (x) {
            case 1 -> System.out.println("one");
            default -> {}
        }
    }
}
