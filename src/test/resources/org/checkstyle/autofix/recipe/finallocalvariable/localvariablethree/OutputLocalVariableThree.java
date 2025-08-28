/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.FinalLocalVariableCheck">
    </module>
  </module>
</module>

*/
package org.checkstyle.autofix.recipe.finallocalvariable.localvariablethree;

public class OutputLocalVariableThree {
    class class2 {
        public void method1() {
            final int x;
            x = 3;
        }

        public void method2() {
            for (int i = 0; i < 5; i++) {
                final int x;
                x = 3;
            }
            int y;
            for (int i = 0; i < 5; i++) {
                y = 3;
            }
            for (int i = 0; i < 5; i++) {
                int z;
                for (int j = 0; j < 5; j++) {
                    z = 3;
                }
            }
        }

        public void method3() {
            int m;
            do {
                m = 0;
            } while (false);
            do {
                final int n;
                n = 0;
            } while (true);
        }

        private void foo() {
            final int q;
            final int w;
            int e;
            q = 1;
            w = 1;
            e = 1;
            e = 2;
            class Local {
                void bar() {
                    int q;
                    final int w;
                    final int e;
                    q = 1;
                    q = 2;
                    w = 1;
                    e = 1;
                }
            }

            int i;
            for (; ; i = 1) {
            }
        }

        public void method4() {
            int m;
            int i = 5;
            while (i > 1) {
                m = 0;
                i++;
            }
            while (true) {
                final int n;
                n = 0;
            }
        }

        int[] array = new int[10];

        public void method5() {
            int r;
            for (int a : array) {
                r = 0;
            }
            for (int a : array) {
                final int t;
                t = 0;
            }
        }
    }

    class classs3 {
        public void method(final int i) {
            switch (i) {
                case 1:
                    final int foo = 1;
                    break;
                default:
            }
            switch (i) {
                case 1:
                    int foo = 1;    // No Violation
                    break;
                case 2:
                    foo = 2;
                    break;
                default:
            }
        }
    }
}
