/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.useenhancedswitch.mutationtests14;

public class InputMutationTests14 {
    enum Color { RED, GREEN, BLUE }

    void test(int x) {
        int y;
        switch (x) { // violation 'Switch can be replaced with enhanced switch.'
            case 1:
            case 2:
                y = 1;
                break;
            case 3:
                y = 2;
                x = 3;
                break;
            default:
                y = 0;
                break;
        }
    }

    void testEnum(Color c) {
        int x;
        switch (c) { // violation 'Switch can be replaced with enhanced switch.'
            case RED:
                x = 1;
                break;
            case GREEN:
                x = 2;
                break;
            case BLUE:
                x = 3;
                break;
        }
    }

    void testIndent(int x) {
        int y;
        switch (x) { // violation 'Switch can be replaced with enhanced switch.'
            case 1:
                y = 1;
                break;
            default:
                y = 0;
                break;
        }
    }

    int testReturn(int x) {
        switch (x) { // violation 'Switch can be replaced with enhanced switch.'
            case 1:
                return 1;
            default:
                return 0;
        }
    }

    void testIndent2(int x) {
        int y;
        switch (x) { // violation 'Switch can be replaced with enhanced switch.'
            case 1:
          y = 1;
                break;
            default:
                y = 0;
                break;
        }
    }

    void testIndent3(int x) {
        int y;
        switch (x) { // violation 'Switch can be replaced with enhanced switch.'
            case 1:
y = 1;
                break;
            default:
                y = 0;
                break;
        }
    }

    void testNoNewline(int x) {
        switch (x) { // violation 'Switch can be replaced with enhanced switch.'
            case 1: x = 1; break;
            default: break;
        }
    }

    void testIndentVisitorNoNewline(int x) {
        int y;
        switch (x) { // violation 'Switch can be replaced with enhanced switch.'
            case 1:
                y = 1; x = 2;
                break;
            default:
                y = 0;
                break;
        }
    }
}
