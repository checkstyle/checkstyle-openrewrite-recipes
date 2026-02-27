/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.killremainingmutations;

public class OutputKillRemainingMutations {

    public void partialMultiVarWithTrailingComment() {
        int used = 1;
        System.out.println(used); /* trailing */
        int b = 2;
        System.out.println(b);
    }

    public void nonIdentifierAssignments() {
        int[] arr = { 1 };
        arr[0] = 2;
        arr[0]++;
        System.out.println(arr[0]);
    }

    public void mergeIntoExistingComments() {
        // comment on unused
        // existing comment
        int used = 3;
        System.out.println(used);
    }

    public void multipleCommentsOnRemoved() {
        int used = 1;
        System.out.println(used); /* c1 */ /* c2 */
        System.out.println("done");
    }

    public void noCommentSameLine() {
        int used = 1;
        System.out.println(used);
    }

    public void commentOnNewLine() {
        int used = 1;
        // comment on unused
        System.out.println(used);
    }

    public void commentWithBlankLine() {
        int used = 1;
        /* block comment */
        System.out.println(used);
    }

    public void twoComments() {
        int used = 1;
        // comment 1
        // comment 2
        System.out.println(used);
    }

    public void trailingCommentOnExtractedSideEffect() {
        int a = 1;
        /* trailing */
        ++a;
        int next = 3;
        System.out.println(next);
        System.out.println(a);
    }

    public void sideEffectWithTrailingComment() {
        int a = 0;
        System.out.println("First");
        /* trailing */
        ++a;
        int next = 2;
        System.out.println(next);
        System.out.println(a);
    }

    public void methodCallWithTrailingComment() {
        System.out.println("First");
        /* trailing */
        System.out.println("Second");
        System.out.println("Third");
    }

    private String getString() {
        return "value";
    }
}
