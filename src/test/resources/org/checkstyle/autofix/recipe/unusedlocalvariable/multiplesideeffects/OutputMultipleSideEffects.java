/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.multiplesideeffects;

public class OutputMultipleSideEffects {

    public void method() {
        int a = 0;
        // Comment before multiple side effects
        ++a; a++;
        System.out.println(a);
    }
}
