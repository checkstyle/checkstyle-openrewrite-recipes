/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.useenhancedswitch.mutationtests25;

public class InputMutationTests25 {
    enum Color { RED, GREEN, BLUE }

    int testCollectHandled(Color c) {
        switch (c) { // violation 'Switch can be replaced with enhanced switch.'
            case RED:
            case GREEN:
                return 1;
            case BLUE:
                return 2;
            default:
                return 0;
        }
    }

    void testEmptyDefault(int x) {
        switch (x) { // violation 'Switch can be replaced with enhanced switch.'
            case 1:
                System.out.println("one");
                break;
            default:
                break;
        }
    }
}
