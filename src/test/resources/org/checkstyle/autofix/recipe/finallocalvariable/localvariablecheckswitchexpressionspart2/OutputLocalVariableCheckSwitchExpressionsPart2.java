/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.FinalLocalVariableCheck">
    </module>
  </module>
</module>

*/
package org.checkstyle.autofix.recipe.finallocalvariable.localvariablecheckswitchexpressionspart2;

public class OutputLocalVariableCheckSwitchExpressionsPart2 {
    void foo3() throws Exception {

        final Exception e;

        final int a = (int) Math.random();
        final int b = (int) Math.random();

        switch (a) {
            case 0 -> e = new Exception();
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
            default -> e = new Exception();
        }

        throw e;
    }

    void foo4() throws Exception {

        final Exception e;

        final int a = (int) Math.random();
        final int b = (int) Math.random();

        switch (a) {
            case 0 -> e = new Exception();
            case 1 -> System.out.println("test!");
            default -> System.out.println("Exception!");

        }

    }

}
