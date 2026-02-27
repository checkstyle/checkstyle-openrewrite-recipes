/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.classfields;

public class InputClassFields {

    private int classField = 10;
    private String classString = "hello";

    public void method() {
        int unused = 20; // violation 'Unused named local variable'
        System.out.println(classField);
    }

    public void methodTwo() {
        String unused = "world"; // violation 'Unused named local variable'
        System.out.println(classString);
    }
}
