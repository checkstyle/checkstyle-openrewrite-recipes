/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.multipleunused;

public class OutputMultipleUnused {

    public void method() {
        int used = 3;
        System.out.println(used);
    }

    public int calculate() {
        int result = 42;
        return result;
    }
}
