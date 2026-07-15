/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.useenhancedswitch.emptycaseanddefault;

public class OutputEmptyCaseAndDefault {
    private boolean isGroupReady(int status) {
        switch (status) {
            case 1, 2, 3, 4 -> {
                return false;
            }
            default -> {}
                // Empty
        }
        return true;
    }
}
