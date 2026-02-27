/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.multipleunused;

public class InputMultipleUnused {

    public void method() {
        int unusedOne = 1; // violation 'Unused named local variable'
        int unusedTwo = 2; // violation 'Unused named local variable'
        int used = 3;
        System.out.println(used);
    }

    public int calculate() {
        int unusedTemp = 100; // violation 'Unused named local variable'
        int result = 42;
        return result;
    }
}
