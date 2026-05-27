/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.FinalLocalVariableCheck">
    </module>
  </module>
</module>

*/
package org.checkstyle.autofix.recipe.finallocalvariable.localvariablecheckswitchexpressions;

public class InputLocalVariableCheckSwitchExpressions {
    void foo1() throws Exception {

        Exception e; // violation, "Variable 'e' should be declared final"

        final int a = (int) Math.random();
        final int b = (int) Math.random();

        switch (a) {
            case 0:
                e = new Exception();
                break;
            case 1:
                if (b == 0) {
                    e = new Exception();
                    break;
                }

                if (b == 1) {
                    e = new Exception();
                } else {
                    e = new Exception();
                }
                break;
            case 2:
                if (b == 0) {
                    return;
                }

                e = new Exception();
                break;
            default:
                e = new Exception();
                break;
        }

        throw e;
    }

    void foo2() throws Exception {

        Exception e; // violation, "Variable 'e' should be declared final"

        final int a = (int) Math.random();
        final int b = (int) Math.random();

        switch (a) {
            case 0 -> {
                e = new Exception();
            }
            case 1 -> {
                if (b == 0) {
                    e = new Exception();
                    break;
                }

                if (b == 1) {
                    e = new Exception();
                } else {
                    e = new Exception();
                }
            }
            case 2 -> {
                if (b == 0) {
                    return;
                }

                e = new Exception();
            }
            default -> {
                e = new Exception();
            }
        }

        throw e;
    }

}
