/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.assignmentwithmethods;

public class InputAssignmentWithMethods {

    public void assignment() {
        int a = 1;
        int b = 2; // violation 'Unused named local variable'
        a = 3;
        b = 4;
        System.out.println(a);
    }

    public void methodCall() {
        String s = getString(); // violation 'Unused named local variable'
        System.out.println("hello");
    }

    public void complex() {
        Object o = null;
        Object p = new Object(); // violation 'Unused named local variable'
        p = getString();
        System.out.println(o);
    }

    private String getString() {
        return "str";
    }
}
