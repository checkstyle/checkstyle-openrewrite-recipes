/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.orphanedassignments;

public class OutputOrphanedAssignments {
    public void method() {
        int b = 2;
        b = 5;
        b += 5;
        System.out.println(b);
    }
}
