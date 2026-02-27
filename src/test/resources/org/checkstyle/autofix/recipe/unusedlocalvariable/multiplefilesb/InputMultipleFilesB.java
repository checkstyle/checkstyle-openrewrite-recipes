/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.multiplefilesb;

public class InputMultipleFilesB {
    public void method() {
        int x = 20; // violation 'Unused named local variable 'x'.'
        System.out.println("done");
    }
}
