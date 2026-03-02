/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.useenhancedswitch.genericswitchstatements;

public class InputGenericSwitchStatements {

    public enum Status { ONE, TWO, THREE, FOUR }

    public void processState(Status s) {
        switch (s) { // violation 'Switch can be replaced with enhanced switch'
            case ONE:
                System.out.println("One");
                break;
            case TWO:
            case THREE:
            case FOUR:
            default:
                System.out.println("Rest");
                break;
        }
    }

    public String computeResult(Status s) {
        switch (s) { // violation 'Switch can be replaced with enhanced switch'
            case ONE:
                return "1";
            case TWO:
                return "2";
            case THREE:
            default:
                return "3";
        }
    }
}
