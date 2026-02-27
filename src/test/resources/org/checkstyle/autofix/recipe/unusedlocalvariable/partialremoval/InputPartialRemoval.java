/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.partialremoval;

public class InputPartialRemoval {

    public void method() {
        int used = 1, unused = 2; // violation 'Unused named local variable'
        used = used + 1;
        int other = 5;
        other = 6;
        System.out.println(used + other);
    }
}
