/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.FinalLocalVariableCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.finallocalvariable.multiplevariable;

public class OutputMultipleVariable {

    public void multipleDeclarations() {

        final int x = 10, y = 20, z = 30;

        final String name = "John", city = "NYC";

        final double price = 19.99, tax = 0.08;

        System.out.println("Sum: " + (x + y + z));
        System.out.println(name + " lives in " + city);
        System.out.println("Total: " + (price + (price * tax)));
    }
}