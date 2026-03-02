/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.useenhancedswitch.mutationtests12;

public class InputMutationTests12 {

    enum Color { RED, GREEN, BLUE }

    public int testFallthroughAssignment(int i) {
        int x = 0;
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (i) {
            case 1:
            case 2:
                x = 1;
                break;
            default:
                x = 2;
                break;
        }
        return x;
    }

    public int testFallthroughReturn(int i) {
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (i) {
            case 1:
            case 2:
                return 1;
            default:
                return 2;
        }
    }

    public void testNonIdentifierAssignment(int i) {
        this.field = 0;
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (i) {
            case 1:
                this.field = 1;
                break;
            default:
                this.field = 2;
                break;
        }
    }

    private int field;

    public int testInexhaustiveEnum(Color c) {
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (c) {
            case RED:
                return 1;
            case GREEN:
                return 2;
        }
        return 0;
    }

    public void testNoSpaceIndent(int i) {
// violation below, 'Switch can be replaced with enhanced switch'
switch(i) {
case 1:
System.out.println("1");
break;
default:
System.out.println("2");
break;
}
    }
}
