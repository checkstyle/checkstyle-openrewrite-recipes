/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.multiplefilesa;

public class InputMultipleFilesA {
    public void method() {
        int x = 10;
        System.out.println(x);
    }
}
