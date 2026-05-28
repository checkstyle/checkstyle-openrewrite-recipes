/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.useenhancedswitch.mutationtests23;

public class OutputMutationTests23 {
    enum Color { RED, GREEN, BLUE }
    enum SmallEnum { A, B }

    void testExhaustiveEnumNoDefault(Color e) {
        int x = switch (e) {
            case RED -> 1;
            case GREEN -> 2;
            case BLUE -> 3;
        };
    }

    void testIndentNoNewline(int x) {
        int y = switch (x) {
            case 1 -> 1;
            default -> 0;
        };
    }

    int testExhaustiveWithDefault(Color e) {
        return switch (e) {
            case RED -> 1;
            case GREEN -> 2;
            case BLUE -> 3;
        };
    }

    int testNonExhaustiveEnumNoDefault(SmallEnum e) {
        switch (e) {
            case A -> {
                return 1;}
        }
        return 0;
    }

    int testExhaustiveEnumReturnNoDefault(Color e) {
        return switch (e) {
            case RED -> 1;
            case GREEN -> 2;
            case BLUE -> 3;
        };
    }

    void testEnumDefaultOnly(Color e) {
        int x = switch (e) {
            default -> 1;
        };
    }

    void testContinueInSwitch() {
        for (int i = 0; i < 5; i++) {
            switch (i) {
                case 1 -> {
                    continue;}
                default -> {
                    int x = i;
                }
            }
        }
    }
}
