/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.multivardeclaration;

public class InputMultiVarDeclaration {

    public void method() {
        int used = 10, unused = 20; // violation 'Unused named local variable'
        System.out.println(used);
    }

    public void methodTwo() {
        int unusedFirst = 1, unusedSecond = 2; // 2 violations
        int usedValue = 3;
        System.out.println(usedValue);
    }
}
