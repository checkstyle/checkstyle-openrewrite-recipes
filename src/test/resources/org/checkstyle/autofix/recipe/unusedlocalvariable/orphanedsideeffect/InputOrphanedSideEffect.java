/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.orphanedsideeffect;

public class InputOrphanedSideEffect {
    public void test() {
        int a; // violation 'Unused named local variable'
        a = sideEffect();
    }

    public void withComment() {
        // important comment
        int b; // violation 'Unused named local variable'
        b = sideEffect();
    }

    public void withTrailingComment() {
        System.out.println("before"); /* trailing */ int c; // violation 'Unused named local variable'
        c = sideEffect();
    }

    public void withLiteralRhs() {
        int d; // violation 'Unused named local variable'
        d = 42;
    }

    public void withParenthesizedRhs() {
        int e; // violation 'Unused named local variable'
        e = (sideEffect());
    }

    public int sideEffect() {
        System.out.println("Side effect!");
        return 1;
    }
}
