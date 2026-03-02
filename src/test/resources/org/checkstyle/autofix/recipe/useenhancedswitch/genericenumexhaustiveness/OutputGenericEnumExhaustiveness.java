/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.useenhancedswitch.genericenumexhaustiveness;

public class OutputGenericEnumExhaustiveness {

    public enum Color { RED, GREEN, BLUE }

    public String evaluateColor(Color currentColor) {
        return switch (currentColor) {
            case RED -> "R";
            case GREEN -> "G";
            case BLUE -> "B";
        };
    }

    public String evaluateColorDynamic(Color currentColor) {
        return switch (currentColor) {
            case RED -> "R";
            case GREEN -> "G";
            case BLUE -> "B";
        };
    }

    public String evaluateColorSideEffect(Color currentColor) {
        return switch (currentColor) {
            case RED -> "R";
            case GREEN -> "G";
            case BLUE -> "B";
        };
    }

    public String evaluateColorInexhaustive(Color currentColor) {
        return switch (currentColor) {
            case RED -> "R";
            case GREEN -> "G";
            default -> throw new AssertionError();
        };
    }
}
