/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.unarysideeffect;

public class InputUnarySideEffect {

    public void method() {
        int i = 0;
        int unused1 = ++i; // violation 'Unused named local variable'
        int unused2 = i++; // violation 'Unused named local variable'
        int unused3 = --i; // violation 'Unused named local variable'
        int unused4 = i--; // violation 'Unused named local variable'
        System.out.println(i);
    }
}
