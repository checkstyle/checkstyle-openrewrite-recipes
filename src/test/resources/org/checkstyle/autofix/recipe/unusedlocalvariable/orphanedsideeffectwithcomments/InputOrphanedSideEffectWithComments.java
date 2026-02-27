/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.orphanedsideeffectwithcomments;

public class InputOrphanedSideEffectWithComments {
    public void leadingCommentBeforeSideEffect() {
        // leading comment
        int a; // violation 'Unused named local variable'
        a = sideEffect();
        System.out.println("after");
    }

    public void trailingCommentBeforeSideEffect() {
        System.out.println("before"); /* trailing comment */ int a; // violation 'Unused named local variable'
        a = sideEffect();
        System.out.println("after");
    }

    public int sideEffect() {
        System.out.println("side effect");
        return 1;
    }
}
