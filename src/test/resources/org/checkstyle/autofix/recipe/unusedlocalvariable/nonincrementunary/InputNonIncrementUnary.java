/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.nonincrementunary;

public class InputNonIncrementUnary {
    public void method() {
        int x = -5; // violation 'Unused named local variable 'x'.'
        boolean b = !true; // violation 'Unused named local variable 'b'.'
        System.out.println("done");
    }
}
