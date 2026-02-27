/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.multivardeclaration;

public class OutputMultiVarDeclaration {

    public void method() {
        int used = 10;
        System.out.println(used);
    }

    public void methodTwo() {
        int usedValue = 3;
        System.out.println(usedValue);
    }
}
