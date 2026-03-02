/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.useenhancedswitch.mutationtests16;

public class InputMutationTests16 {

    void testDifferentIdentifiers(int x) {
        int y;
        int z;
        switch (x) { // violation 'Switch can be replaced with enhanced switch.'
            case 1:
                y = 1;
                break;
            case 2:
                z = 2;
                break;
            default:
                y = 0;
                break;
        }
    }

    int testPartialReturnDrop(int x) {
        switch (x) { // violation 'Switch can be replaced with enhanced switch.'
            case 1:
                return 1;
            case 2:
                System.out.println("Wait!");
                break;
            default:
                return 0;
        }
        return -1;
    }

    void testMeaningfulStatementsBreakLabel(int x) {
        outer:
        switch (x) { // violation 'Switch can be replaced with enhanced switch.'
            case 1:
                System.out.println(1);
                break outer;
            default:
                System.out.println(0);
                break;
        }
    }

    int testIntSwitchNoDefault(int x) {
        switch (x) { // violation 'Switch can be replaced with enhanced switch.'
            case 1:
                return 1;
            case 2:
                return 2;
        }
        return 0;
    }

    void testCollectHandledLiteral(String str) {
        switch (str) { // violation 'Switch can be replaced with enhanced switch.'
            case "A":
                System.out.println("A");
                break;
            case "B":
                System.out.println("B");
                break;
            default:
                break;
        }
    }

    void testBlockEndSpacePaddingEdge(int x) {
        switch (x) { // violation 'Switch can be replaced with enhanced switch.'
            case 1: {
                int y = 1; break; /* pad */ }
            default:
                System.out.println(0);
                break;
        }
    }

    void testAdjustSpaceEdge(int x) {
        switch (x) { // violation 'Switch can be replaced with enhanced switch.'
            case 1: 
                { int y = 1; break; }
            default: 
                break;
        }
    }
}
