/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.useenhancedswitch.allbranchesthrow;

public class InputAllBranchesThrow {

    public enum ErrorType {
        IO_EXCEPTION, RUNTIME_EXCEPTION
    }

    public Object testReturn(ErrorType type) throws Exception {
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (type) {
            case IO_EXCEPTION:
                throw new Exception("IO");
            case RUNTIME_EXCEPTION:
                throw new RuntimeException("Runtime");
        }
        return null;
    }

    public void testAssignment(ErrorType type) throws Exception {
        int x;
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (type) {
            case IO_EXCEPTION:
                throw new Exception("IO");
            case RUNTIME_EXCEPTION:
                throw new RuntimeException("Runtime");
        }
        x = 1;
    }
}
