/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.compoundassignment;

public class OutputCompoundAssignment {

    public void compoundOnUnused() {
        int a = 1;
        a += 5;
        a -= 3;
        a *= 2;
        System.out.println(a);
    }

    public void compoundOnUsed() {
        int used = 10;
        used += 5;
        System.out.println(used);
    }
}
