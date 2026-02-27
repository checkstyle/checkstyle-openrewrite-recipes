/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.unarysideeffect;

public class OutputUnarySideEffect {

    public void method() {
        int i = 0;
        ++i;
        i++;
        --i;
        i--;
        System.out.println(i);
    }
}
