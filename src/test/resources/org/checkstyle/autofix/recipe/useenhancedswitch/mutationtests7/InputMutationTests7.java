/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.useenhancedswitch.mutationtests7;

public class InputMutationTests7 {

    int missingDefaultReturn(int x) {
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1:
                return 1;
        }
        return 0;
    }

    void notAllCasesReturn(int x) {
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1:
                System.out.println("1");
                break;
            default:
                break;
        }
    }

    int emptyBlockReturn(int x) {
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1:
                {}
                break;
            default:
                return 0;
        }
        return -1;
    }

    void tabIndentedSwitch(int x) {
    	// violation below, 'Switch can be replaced with enhanced switch'
    	switch (x) {
    		case 1:
    			System.out.println("1");
    			break;
    		default:
    			break;
    	}
    }
}
