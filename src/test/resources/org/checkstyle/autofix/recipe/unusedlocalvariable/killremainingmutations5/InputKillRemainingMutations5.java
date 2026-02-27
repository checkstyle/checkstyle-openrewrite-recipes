/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.killremainingmutations5;

public class InputKillRemainingMutations5 {

    public void trailingCommentAtStartOfBlock() {
        {
            int unused = 1; /* trailing */ // violation 'Unused named local variable'
            System.out.println("after");
        }
    }

    public void partialDeclarationWithTrailingOnRemoved() {
        int keep = 1, drop = 2; /* trailing drop */ // violation 'Unused named local variable'
        System.out.println(keep);
    }

    public void extractedInitializerWithTrailing() {
        int unused = getString().length(); /* trailing */ // violation 'Unused named local variable'
        System.out.println("after");
    }

    public void trailingCommentTransferredToPartial() {
        int keep1 = 1; /* trailing */ int unused = 2; // violation 'Unused named local variable'
        int keep2 = 3, drop = 4; // violation 'Unused named local variable'
        System.out.println(keep1);
        System.out.println(keep2);
    }

    public void trailingCommentTransferredToExtracted() {
        int keep1 = 1; /* trailing */ int unused1 = 2; // violation 'Unused named local variable'
        int unused2 = getString().length(); // violation 'Unused named local variable'
        System.out.println(keep1);
    }

    public void mismatchedIndent() {
        int a = 1; // violation 'Unused named local variable'
            int b = 2;
        System.out.println(b);
    }

    public void hasRemovedPredecessor() {
        int a = 1; // violation 'Unused named local variable'
        System.out.println("kept1"); System.out.println("kept2"); /* comment */
        int b = getString().length(); // violation 'Unused named local variable'
    }

    public void ensureLeadingNewline() {
        int a = 1; // violation 'Unused named local variable'
        /* comment */
            int b = getString().length(); // violation 'Unused named local variable'
    }

    public void absorbSameLine() {
        int a = 1; int b = 2; // violation 'Unused named local variable'
        System.out.println(b);
    }

    public void absorbWithComment() {
        int a = 1; // violation 'Unused named local variable'
        /* comment */ int b = 2; // violation 'Unused named local variable'
            int c = 3;
        System.out.println(c);
    }

    public void partialVariableDeclarationWithPrefixComment() {

        /* comment */
        int keep = 1, unused = 2; // violation 'Unused named local variable'
        System.out.println(keep);
    }

    public void partialSingleLineWithExtracted() {
        System.out.println("prefix"); int keep = 1, unused = getString().length(); // violation 'Unused named local variable'
        System.out.println(keep);
    }

    public void blankLineAfterRemoved() {
        System.out.println("first");
        int unused = 1; // violation 'Unused named local variable'


        System.out.println("keep");
    }

    private String getString() {
        return "value";
    }
}
