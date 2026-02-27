/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.killremainingmutations5;

public class OutputKillRemainingMutations5 {

    public void trailingCommentAtStartOfBlock() {
        {
            /* trailing */
            System.out.println("after");
        }
    }

    public void partialDeclarationWithTrailingOnRemoved() {
        int keep = 1; /* trailing drop */
        System.out.println(keep);
    }

    public void extractedInitializerWithTrailing() {
        getString().length(); /* trailing */
        System.out.println("after");
    }

    public void trailingCommentTransferredToPartial() {
        int keep1 = 1; /* trailing */
        int keep2 = 3;
        System.out.println(keep1);
        System.out.println(keep2);
    }

    public void trailingCommentTransferredToExtracted() {
        int keep1 = 1; /* trailing */
        getString().length();
        System.out.println(keep1);
    }

    public void mismatchedIndent() {
            int b = 2;
        System.out.println(b);
    }

    public void hasRemovedPredecessor() {
        System.out.println("kept1"); System.out.println("kept2"); /* comment */
        getString().length();
    }

    public void ensureLeadingNewline() {
        /* comment */
            getString().length();
    }

    public void absorbSameLine() {
        int b = 2;
        System.out.println(b);
    }

    public void absorbWithComment() {
        /* comment */
            int c = 3;
        System.out.println(c);
    }

    public void partialVariableDeclarationWithPrefixComment() {

        /* comment */
        int keep = 1;
        System.out.println(keep);
    }

    public void partialSingleLineWithExtracted() {
        System.out.println("prefix"); int keep = 1;
getString().length();
        System.out.println(keep);
    }

    public void blankLineAfterRemoved() {
        System.out.println("first");


        System.out.println("keep");
    }

    private String getString() {
        return "value";
    }
}
