/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.useenhancedswitch.mutationtests9;

import java.util.Collections;

public class InputMutationTests9 {
    public enum Day { MON, TUE, WED }

    public int testNestedReturn(int x) {
        int res;
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1:
                if (x > 0) return 0; // nested return
                res = 1;
                break;
            default:
                res = 2;
                break;
        }
        return res;
    }

    public int testExhaustiveEnumWithDefault(Day day) {
        int res;
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (day) {
            case MON:
                res = 1;
                break;
            case TUE:
                res = 2;
                break;
            case WED:
                res = 3;
                break;
            default:
                res = 0;
                break;
        }
        return res;
    }

    public int testNonEnumSwitch(String s) {
        int res;
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (s) {
            case "A":
                res = 1;
                break;
            default:
                res = 0;
                break;
        }
        return res;
    }

    public int testMergeAdjacent(int x) {
        int res = 0;
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 1: res = 1; break;
        }
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) {
            case 2: res = 2; break;
        }
        return res;
    }
}
