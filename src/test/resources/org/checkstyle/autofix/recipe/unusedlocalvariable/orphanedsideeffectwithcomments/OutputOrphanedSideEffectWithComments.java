/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.orphanedsideeffectwithcomments;

public class OutputOrphanedSideEffectWithComments {
    public void leadingCommentBeforeSideEffect() {
        // leading comment
        sideEffect();
        System.out.println("after");
    }

    public void trailingCommentBeforeSideEffect() {
        System.out.println("before"); /* trailing comment */
        sideEffect();
        System.out.println("after");
    }

    public int sideEffect() {
        System.out.println("side effect");
        return 1;
    }
}
