/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.FinalLocalVariableCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.finallocalvariable.multiplevariable;

public class InputMultipleVariable {

    public void multipleDeclarations() {

        int x = 10, y = 20, z = 30;
        // 3 violations above:
        //    "Variable 'x' should be declared final"
        //    "Variable 'y' should be declared final"
        //    "Variable 'z' should be declared final"

        String name = "John", city = "NYC";
        // 2 violations above:
        //    "Variable 'name' should be declared final"
        //    "Variable 'city' should be declared final"

        double price = 19.99, tax = 0.08;     // violation "should be declared final"

        tax = 50;
        System.out.println("Sum: " + (x + y + z));
        System.out.println(name + " lives in " + city);
        System.out.println("Total: " + (price + (price * tax)));
    }
}
