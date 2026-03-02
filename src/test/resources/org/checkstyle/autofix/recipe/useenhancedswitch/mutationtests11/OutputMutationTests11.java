/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.useenhancedswitch.mutationtests11;

public class OutputMutationTests11 {
	public void testTabs(int i) {
		int x = 0;
		switch(i) {
			case 1 -> x = 1;
			default -> x = 2;
		}
	}

    public void testSingleLineBlock(int i) {
        int x = 0;
        switch(i) {
            case 1 -> x = 1;
            default -> x = 2;
        }
    }
}
