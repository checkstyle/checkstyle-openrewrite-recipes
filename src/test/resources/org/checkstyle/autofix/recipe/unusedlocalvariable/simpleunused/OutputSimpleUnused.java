/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.simpleunused;

public class OutputSimpleUnused {

    public void method() {
        int used = 10;
        System.out.println(used);
    }

    public void methodTwo() {
        String usedStr = "world";
        System.out.println(usedStr);
    }
}
