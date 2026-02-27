/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.methodnewassignmentsideeffects;

public class OutputMethodNewAssignmentSideEffects {

    public int b;

    public void method() {
        int a = 0;
        a = 5;
        new Object();
        Math.abs(a);
        this.b++;
        this.b = 5;
        System.out.println(a);
    }
}
