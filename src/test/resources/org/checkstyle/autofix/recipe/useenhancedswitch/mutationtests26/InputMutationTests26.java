/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.useenhancedswitch.mutationtests26;

public class InputMutationTests26 {

    void assignmentArrowBlock(int x) {
        int val;
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1: {
                val = 10;
                break;
            }
            case 2: {
                System.out.println("two");
                val = 20;
                break;
            }
            default: {
                val = 0;
                break;
            }
        }
        System.out.println(val);
    }

    int blockReturnExpr(int x) {
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1:
                System.out.println("case1");
                return 1;
            case 2:
                return 2;
            default:
                System.out.println("default");
                return 0;
        }
    }

    void assignmentWithThrow(int x) {
        String val;
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1:
                val = "one";
                break;
            default:
                throw new RuntimeException("error");
        }
        System.out.println(val);
    }

    void multiStatement(int x) {
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1:
                System.out.println("a");
                System.out.println("b");
                break;
            default:
                System.out.println("default");
                break;
        }
    }
}
