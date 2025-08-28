/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.FinalLocalVariableCheck">
    </module>
  </module>
</module>

*/
package org.checkstyle.autofix.recipe.finallocalvariable.localvariablefour;

public class OutputLocalVariableFour {
    class Class3 {
        public void test1() {
            final boolean b = true;
            final int shouldBeFinal;

            if (b) {
                shouldBeFinal = 1;
            }
            else {
                shouldBeFinal = 2;
            }
        }

        public void test2() {
            final int b = 10;
            final int shouldBeFinal;

            switch (b) {
                case 0:
                    shouldBeFinal = 1;
                    break;
                default:
                    shouldBeFinal = 2;
                    break;
            }
        }

        public void test3() {
            int x;        // No Violation
            try {
                x = 0;
            } catch (final Exception e) {
                x = 1;
            }

            int y;        // No Violation
            try {
                y = 0;
            } finally {
                y = 1;
            }
        }

        public void test4() {
            final boolean b = false;
            int x;        // No Violation
            if (b) {
                x = 1;
            } else {
                x = 2;
            }

            if(b) {
                x = 3;
            }
        }

        public void test5() {
            final boolean b = false;
            final int shouldBeFinal;
            if(b) {
            }
            if (b) {
                shouldBeFinal = 1;
            } else {
                shouldBeFinal = 2;
            }
        }
    }

    class class4 {
        public void foo() {
            final int shouldBeFinal;
            class Bar {
                void bar () {
                    final int shouldBeFinal;
                    final boolean b = false;
                    if (b) {
                        shouldBeFinal = 1;
                    } else {
                        shouldBeFinal = 2;
                    }
                }
            }
        }
    }
}
