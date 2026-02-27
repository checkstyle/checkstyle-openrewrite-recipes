/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.compoundassignment;

public class InputCompoundAssignment {

    public void compoundOnUnused() {
        int a = 1;
        int b = (a += 5); // violation 'Unused named local variable'
        int c = (a -= 3); // violation 'Unused named local variable'
        int d = (a *= 2); // violation 'Unused named local variable'
        System.out.println(a);
    }

    public void compoundOnUsed() {
        int used = 10;
        used += 5;
        System.out.println(used);
    }
}
