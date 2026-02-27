/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.simpleunused;

public class InputSimpleUnused {

    public void method() {
        int used = 10;
        int unused = 20; // violation 'Unused named local variable'
        System.out.println(used);
    }

    public void methodTwo() {
        String unusedStr = "hello"; // violation 'Unused named local variable'
        String usedStr = "world";
        System.out.println(usedStr);
    }
}
