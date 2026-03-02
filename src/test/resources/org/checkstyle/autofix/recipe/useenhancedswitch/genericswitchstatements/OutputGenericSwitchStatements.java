/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.useenhancedswitch.genericswitchstatements;

public class OutputGenericSwitchStatements {

    public enum Status { ONE, TWO, THREE, FOUR }

    public void processState(Status s) {
        switch (s) {
            case ONE -> System.out.println("One");
            default -> System.out.println("Rest");
        }
    }

    public String computeResult(Status s) {
        return switch (s) {
            case ONE -> "1";
            case TWO -> "2";
            default -> "3";
        };
    }
}
