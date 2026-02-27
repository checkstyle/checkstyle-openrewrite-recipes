/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.orphanedassignments;

public class InputOrphanedAssignments {
    public void method() {
        int a = 1; // violation 'Unused named local variable 'a'.'
        int b = 2;
        int c = 3; // violation 'Unused named local variable 'c'.'
        a = 10;
        b = 5;
        b += 5;
        c++;
        System.out.println(b);
    }
}
