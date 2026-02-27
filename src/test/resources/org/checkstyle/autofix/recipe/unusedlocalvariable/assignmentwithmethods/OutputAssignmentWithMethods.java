/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.assignmentwithmethods;

public class OutputAssignmentWithMethods {

    public void assignment() {
        int a = 1;
        a = 3;
        System.out.println(a);
    }

    public void methodCall() {
        getString();
        System.out.println("hello");
    }

    public void complex() {
        Object o = null;
        new Object();
        getString();
        System.out.println(o);
    }

    private String getString() {
        return "str";
    }
}
