/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.orphanedsideeffect;

public class OutputOrphanedSideEffect {
    public void test() {
        sideEffect();
    }

    public void withComment() {
        // important comment
        sideEffect();
    }

    public void withTrailingComment() {
        System.out.println("before"); /* trailing */
        sideEffect();
    }

    public void withLiteralRhs() {
    }

    public void withParenthesizedRhs() {
        sideEffect();
    }

    public int sideEffect() {
        System.out.println("Side effect!");
        return 1;
    }
}
