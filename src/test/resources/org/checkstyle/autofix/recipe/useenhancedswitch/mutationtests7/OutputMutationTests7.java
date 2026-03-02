/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.useenhancedswitch.mutationtests7;

public class OutputMutationTests7 {

    int missingDefaultReturn(int x) {
        switch (x) {
            case 1 -> {
                return 1;
            }
        }
        return 0;
    }

    void notAllCasesReturn(int x) {
        switch (x) {
            case 1 -> System.out.println("1");
            default -> {}
        }
    }

    int emptyBlockReturn(int x) {
        switch (x) {
            case 1 -> {
                {}
            }
            default -> {
                return 0;
            }
        }
        return -1;
    }

    void tabIndentedSwitch(int x) {
    	switch (x) {
    		case 1 -> System.out.println("1");
    		default -> {}
    	}
    }
}
