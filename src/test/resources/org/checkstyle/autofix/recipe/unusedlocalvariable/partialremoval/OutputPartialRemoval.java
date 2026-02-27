/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.partialremoval;

public class OutputPartialRemoval {

    public void method() {
        int used = 1;
        used = used + 1;
        int other = 5;
        other = 6;
        System.out.println(used + other);
    }
}
