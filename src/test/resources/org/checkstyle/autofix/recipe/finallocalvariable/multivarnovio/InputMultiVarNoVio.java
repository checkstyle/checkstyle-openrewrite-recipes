/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.FinalLocalVariableCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.finallocalvariable.multivarnovio;

public class InputMultiVarNoVio {

    public void method() {

        int a = 1, b = 2, c = 3;

        a = 10;
        b = 20;
        c = 30;
        System.out.println(a + b + c);
    }
}
