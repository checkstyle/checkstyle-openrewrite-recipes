/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.useenhancedswitch.genericenumexhaustiveness;

public class InputGenericEnumExhaustiveness {

    public enum Color { RED, GREEN, BLUE }

    public String evaluateColor(Color currentColor) {
        switch (currentColor) { // violation 'Switch can be replaced with enhanced switch'
            case RED:
                return "R";
            case GREEN:
                return "G";
            case BLUE:
                return "B";
            default:
                throw new AssertionError();
        }
    }

    public String evaluateColorDynamic(Color currentColor) {
        switch (currentColor) { // violation 'Switch can be replaced with enhanced switch'
            case RED:
                return "R";
            case GREEN:
                return "G";
            case BLUE:
                return "B";
            default:
                throw new IllegalStateException("Unknown: " + currentColor);
        }
    }

    public String evaluateColorSideEffect(Color currentColor) {
        switch (currentColor) { // violation 'Switch can be replaced with enhanced switch'
            case RED:
                return "R";
            case GREEN:
                return "G";
            case BLUE:
                return "B";
            default:
                System.out.println("Logging: " + currentColor);
                return "U";
        }
    }

    public String evaluateColorInexhaustive(Color currentColor) {
        switch (currentColor) { // violation 'Switch can be replaced with enhanced switch'
            case RED:
                return "R";
            case GREEN:
                return "G";
            default:
                throw new AssertionError();
        }
    }
}
