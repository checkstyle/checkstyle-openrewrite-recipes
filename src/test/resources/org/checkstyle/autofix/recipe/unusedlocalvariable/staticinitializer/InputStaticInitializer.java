/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.staticinitializer;

public class InputStaticInitializer {

    static {
        int unused = 0; // violation 'Unused named local variable'
    }

    public void method() {
        int unused = 0; // violation 'Unused named local variable'
    }
}
