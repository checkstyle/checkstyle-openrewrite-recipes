/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.useenhancedswitch.mutationtests20;

public class OutputMutationTests20 {

    void assignmentSwitchBlock(int x) {
        String s = switch (x) {
            case 1 -> "one";
            case 2 -> {
                System.out.println("two");
                yield "two";
            }
            default -> "default";
        };
    }

    void assignmentSwitchBlockMulti(int x) {
        String s = switch (x) {
            case 1 -> {
                System.out.println("log1");
                System.out.println("log2");
                yield "one";
            }
            default -> "default";
        };
    }

    void assignmentSwitchInitialized(int x) {
        String s = "init";
        switch (x) {
            case 1 -> s = "one";
            default -> s = "default";
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
