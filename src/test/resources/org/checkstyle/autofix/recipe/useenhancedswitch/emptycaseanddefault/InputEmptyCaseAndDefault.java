/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.useenhancedswitch.emptycaseanddefault;

public class InputEmptyCaseAndDefault {
    private boolean isGroupReady(int status) {
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (status) {
            case 1:
            case 2:
            case 3:
            case 4:
                return false;
            default:
                // Empty
        }
        return true;
    }
}
