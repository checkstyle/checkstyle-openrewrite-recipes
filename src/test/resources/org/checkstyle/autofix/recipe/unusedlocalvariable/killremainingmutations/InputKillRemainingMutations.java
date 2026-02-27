/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.killremainingmutations;

public class InputKillRemainingMutations {

    public void partialMultiVarWithTrailingComment() {
        int used = 1;
        System.out.println(used); /* trailing */
        int unused = 2; // violation 'Unused named local variable'
        int a = 1, b = 2; // violation 'Unused named local variable'
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
        int unused = 1; // violation 'Unused named local variable'
        int unused2 = 2; // violation 'Unused named local variable'
        // existing comment
        int used = 3;
        System.out.println(used);
    }

    public void multipleCommentsOnRemoved() {
        int used = 1;
        System.out.println(used); /* c1 */ /* c2 */
        int unused = 2; // violation 'Unused named local variable'
        System.out.println("done");
    }

    public void noCommentSameLine() {
        int used = 1;
        int unused = 2; // violation 'Unused named local variable'
        System.out.println(used);
    }

    public void commentOnNewLine() {
        int used = 1;
        // comment on unused
        int unused = 2; // violation 'Unused named local variable'
        System.out.println(used);
    }

    public void commentWithBlankLine() {
        int used = 1;
        /* block comment */

        int unused = 2; // violation 'Unused named local variable'
        System.out.println(used);
    }

    public void twoComments() {
        int used = 1;
        // comment 1
        // comment 2
        int unused = 2; // violation 'Unused named local variable'
        System.out.println(used);
    }

    public void trailingCommentOnExtractedSideEffect() {
        int a = 1;
        int unused1 = 2; /* trailing */ // violation 'Unused named local variable'
        int unused2 = ++a; // violation 'Unused named local variable'
        int next = 3;
        System.out.println(next);
        System.out.println(a);
    }

    public void sideEffectWithTrailingComment() {
        int a = 0;
        System.out.println("First");
        /* trailing */ int unused = 1; // violation 'Unused named local variable'
        int unused2 = ++a; // violation 'Unused named local variable'
        int next = 2;
        System.out.println(next);
        System.out.println(a);
    }

    public void methodCallWithTrailingComment() {
        System.out.println("First");
        /* trailing */ int unused = 1; // violation 'Unused named local variable'
        System.out.println("Second");
        System.out.println("Third");
    }

    private String getString() {
        return "value";
    }
}
