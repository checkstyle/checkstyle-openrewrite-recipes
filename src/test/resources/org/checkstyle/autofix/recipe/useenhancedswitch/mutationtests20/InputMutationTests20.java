/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.useenhancedswitch.mutationtests20;

public class InputMutationTests20 {

    void assignmentSwitchBlock(int x) {
        String s;
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1: {
                s = "one";
                break;
            }
            case 2: {
                System.out.println("two");
                s = "two";
                break;
            }
            default: {
                s = "default";
                break;
            }
        }
    }

    void assignmentSwitchBlockMulti(int x) {
        String s;
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1: {
                System.out.println("log1");
                System.out.println("log2");
                s = "one";
                break;
            }
            default: {
                s = "default";
                break;
            }
        }
    }

    void assignmentSwitchInitialized(int x) {
        String s = "init";
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1:
                s = "one";
                break;
            default:
                s = "default";
                break;
        }
    }

    void fallThroughSwitch(int x) {
        switch (x) {
            case 1:
                System.out.println("log1");
            case 2:
                System.out.println("log2");
                break;
        }
    }
}
