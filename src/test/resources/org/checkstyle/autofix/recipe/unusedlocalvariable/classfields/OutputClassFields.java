/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.classfields;

public class OutputClassFields {

    private int classField = 10;
    private String classString = "hello";

    public void method() {
        System.out.println(classField);
    }

    public void methodTwo() {
        System.out.println(classString);
    }
}
