/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.methodnewassignmentsideeffects;

public class InputMethodNewAssignmentSideEffects {

    public int b;

    public void method() {
        int a = 0;
        int unusedAssignment = (a = 5); // violation 'Unused named local variable'
        Object unusedNew = new Object(); // violation 'Unused named local variable'
        int unusedMethod = Math.abs(a); // violation 'Unused named local variable'
        int unusedFieldUnary = this.b++; // violation 'Unused named local variable'
        int unusedFieldAssignment = (this.b = 5); // violation 'Unused named local variable'
        System.out.println(a);
    }
}
