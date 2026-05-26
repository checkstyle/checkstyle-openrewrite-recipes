/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.FinalLocalVariableCheck">
    </module>
  </module>
</module>

*/
package org.checkstyle.autofix.recipe.finallocalvariable.localvariableassignedmultipletimespart1;

public class OutputLocalVariableAssignedMultipleTimesPart1 {
    void foo1() {
        final boolean some_condition = true;
        int i;
        if (some_condition) {
            i = 1;
        }
        i = 2;
    }

    void foo2() {
        final boolean some_condition = true;
        int i;
        if (some_condition) {
            i = 1;
        }
        else {

        }
        i = 2;
    }

    void foo3() {
        final boolean some_condition = true;
        int i;
        if (some_condition) {
            i = 1;
            if (i >= 1) {

            }
            else {

            }
        }
        i = 2;
    }


    void foo4() {
        final boolean some_condition = true;
        final int i;
        if (some_condition) {
            if (true) {
            }
            else {
            }
            i = 1;
        }
        else {
            i = 2;
        }
        if (true) {

        }
        else {
        }

    }

    void foo5() {
        final boolean some_condition = true;
        int i;

        {
            i = 2;
        }

        if (some_condition) {
            i = 1;
        }
    }

    void foo6() {
        final boolean some_condition = true;
        int i;

        {
            i = 2;
        }

        if (some_condition) {
            i = 1;
        }
        else {
            i = 6;
        }
    }
}
