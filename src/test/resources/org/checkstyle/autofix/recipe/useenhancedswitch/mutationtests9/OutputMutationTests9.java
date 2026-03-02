/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.useenhancedswitch.mutationtests9;

import java.util.Collections;

public class OutputMutationTests9 {
    public enum Day { MON, TUE, WED }

    public int testNestedReturn(int x) {
        int res;
        switch (x) {
            case 1 -> {
                if (x > 0) return 0; // nested return
                res = 1;
            }
            default -> res = 2;
        }
        return res;
    }

    public int testExhaustiveEnumWithDefault(Day day) {
        int res = switch (day) {
            case MON -> 1;
            case TUE -> 2;
            case WED -> 3;
            default -> 0;
        };
        return res;
    }

    public int testNonEnumSwitch(String s) {
        int res = switch (s) {
            case "A" -> 1;
            default -> 0;
        };
        return res;
    }

    public int testMergeAdjacent(int x) {
        int res = 0;
        switch (x) {
            case 1 -> res = 1;
        }
        switch (x) {
            case 2 -> res = 2;
        }
        return res;
    }
}
