/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.recipeenhancements;

import java.util.ArrayList;
import java.util.List;

public class InputRecipeEnhancements {

    public void redundantAssignment() {
        int a = 1;
        int b = 2; // violation 'Unused named local variable'
        a = 3;
        b = 4;
        System.out.println(a);
    }

    public void methodAndNewInitializers() {
        String s = getString(); // violation 'Unused named local variable'
        List<String> list = new ArrayList<>(); // violation 'Unused named local variable'
        System.out.println("No use of s or list");
    }

    public void nestedBlockAndTypeCast(boolean flag) {
        Object obj = "test"; // violation 'Unused named local variable'
        if (flag) {
            obj = (String) getString();
        }
        System.out.println(flag);
    }

    private String getString() {
        return "value";
    }
}
