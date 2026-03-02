/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.useenhancedswitch.mutationtests11;

public class InputMutationTests11 {
	public void testTabs(int i) {
		int x = 0;
		// violation below, 'Switch can be replaced with enhanced switch'
		switch(i) {
			case 1:
				x = 1;
				break;
			default:
				x = 2;
				break;
		}
	}

    public void testSingleLineBlock(int i) {
        int x = 0;
        // violation below, 'Switch can be replaced with enhanced switch'
        switch(i) {
            case 1: { x = 1; break; }
            default: { x = 2; break; }
        }
    }
}
