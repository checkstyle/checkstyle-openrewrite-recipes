/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.methodscope;

public class OutputMethodScope {

    public void methodOne() {
        System.out.println("one");
    }

    public void methodTwo() {
        int x = 1;
        x = 2;
        System.out.println(x);
    }

    public void methodThree() {
        int used = 1;
        used = 2;
        System.out.println(used);
    }
}
