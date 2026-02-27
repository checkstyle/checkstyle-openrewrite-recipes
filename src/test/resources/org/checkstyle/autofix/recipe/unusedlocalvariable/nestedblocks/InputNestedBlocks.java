/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.nestedblocks;

public class InputNestedBlocks {

    public void method(boolean flag) {
        if (flag) {
            int unused = 10; // violation 'Unused named local variable'
            int used = 20;
            System.out.println(used);
        }
    }

    public void methodTwo() {
        for (int i = 0; i < 5; i++) {
            int unused = i * 2; // violation 'Unused named local variable'
            int used = i + 1;
            System.out.println(used);
        }
    }
}
