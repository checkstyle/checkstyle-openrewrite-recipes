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

public class InputGenericSwitchExpressions {

    public enum State { A, B }

    public Supplier<String> supplyStringDependingOn(int score) {
        return () -> {
            switch (score) { // violation 'Switch can be replaced with enhanced switch'
                case 1:
                    return "one";
                case 2:
                    return "two";
                default:
                    return "many";
            }
        };
    }

    public Optional<String> evaluateItemState(State itemState) {
        final Optional<String> result;
        switch (itemState) { // violation 'Switch can be replaced with enhanced switch'
            case A:
                result = Optional.of("A");
                break;
            case B:
                result = Optional.of("B");
                break;
            default:
                return Optional.empty();
        }
        return result;
    }

    public void executeComplexLogic(int category, State itemState) {
        switch (category) { // violation 'Switch can be replaced with enhanced switch'
            case 1:
                System.out.println("1");
                break;
            case 2:
                switch (itemState) { // violation 'Switch can be replaced with enhanced switch'
                    case A:
                        System.out.println("A");
                        break;
                    case B:
                        System.out.println("B");
                        break;
                    default:
                        throw new RuntimeException("Unknown");
                }
                break;
            default:
                throw new IllegalArgumentException();
        }
    }
}
