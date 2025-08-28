/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.FinalLocalVariableCheck">
    </module>
  </module>
</module>

*/
package org.checkstyle.autofix.recipe.finallocalvariable.localvariablefive;

public class OutputLocalVariableFive {
    class class5 {
        public void test1(){
            final boolean b = false;
            final int shouldBeFinal;
            if(b){
                if(b){
                    shouldBeFinal = 1;
                } else {
                    shouldBeFinal = 2;
                }
            }
        }
        public void test2() {
            final int b = 10;
            final int shouldBeFinal;

            switch (b) {
                case 0:
                    switch (b) {
                        case 0:
                            shouldBeFinal = 1;
                            break;
                        default:
                            shouldBeFinal = 2;
                            break;
                    }
                    break;
                default:
                    shouldBeFinal = 3;
                    break;
            }
        }
        public void test3() {
            int x;    //No Violation
            try {
                x = 0;
                try {
                    x = 0;
                } catch (final Exception e) {
                    x = 1;
                }
            } catch (final Exception e) {
                x = 1;
            }
        }
        public void test4() {
            final int shouldBeFinal;
            class Bar {
                void bar () {
                    final int shouldBeFinal;
                    final boolean b = false;
                    if (b) {
                        if (b) {
                            shouldBeFinal = 1;
                        } else {
                            shouldBeFinal = 2;
                        }
                    } else {
                        shouldBeFinal = 2;
                    }
                }
            }

            abstract class Bar2 {
                abstract void method(String param);
            }
        }

        public void test6() {
            byte tmpByte[];

            if (true) {
                if (true) {
                    tmpByte = new byte[0];
                }
            } else {
                if (true) {
                    if (true) {
                        tmpByte = new byte[1];
                    }
                }
                if (false) {
                    if (true) {
                        tmpByte = new byte[2];
                    }
                }
            }
        }
    }

}
