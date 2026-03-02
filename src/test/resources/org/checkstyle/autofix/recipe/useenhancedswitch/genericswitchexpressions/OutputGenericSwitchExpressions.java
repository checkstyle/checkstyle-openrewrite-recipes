/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.useenhancedswitch.genericswitchexpressions;

import java.util.Optional;
import java.util.function.Supplier;

public class OutputGenericSwitchExpressions {

    public enum State { A, B }

    public Supplier<String> supplyStringDependingOn(int score) {
        return () -> {
            return switch (score) {
                case 1 -> "one";
                case 2 -> "two";
                default -> "many";
            };
        };
    }

    public Optional<String> evaluateItemState(State itemState) {
        final Optional<String> result;
        switch (itemState) {
            case A -> result = Optional.of("A");
            case B -> result = Optional.of("B");
            default -> {
                return Optional.empty();
            }
        }
        return result;
    }

    public void executeComplexLogic(int category, State itemState) {
        switch (category) {
            case 1 -> System.out.println("1");
            case 2 -> {
                switch (itemState) {
                    case A -> System.out.println("A");
                    case B -> System.out.println("B");
                    default -> throw new RuntimeException("Unknown");
                }
            }
            default -> throw new IllegalArgumentException();
        }
    }
}
