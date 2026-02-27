/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.multiplesideeffects;

public class InputMultipleSideEffects {

    public void method() {
        int a = 0;
        // Comment before multiple side effects
        int unused1 = ++a, unused2 = a++; // 2 violations
        System.out.println(a);
    }
}
