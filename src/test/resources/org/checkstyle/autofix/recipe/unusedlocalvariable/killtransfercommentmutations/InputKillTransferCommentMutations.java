/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.killtransfercommentmutations;

public class InputKillTransferCommentMutations {

    public void leadingCommentBeforeOrphaned() {
        int a = 1; // violation 'Unused named local variable'
        int b = 2;
        // comment before orphaned
        a = 10;
        System.out.println(b);
    }

    public void trailingCommentBeforeOrphaned() {
        int a = 1; // violation 'Unused named local variable'
        int b = 2; /* same line comment */ a = 10;
        System.out.println(b);
    }

    public void multipleCommentsFlipTrailing() {
        int a = 1; // violation 'Unused named local variable'
        int b = 2; /* trailing c1 */
        /* leading c2 */ a = 10;
        System.out.println(b);
    }

    public void commentBeforeOrphanedUnary() {
        int a = 1; // violation 'Unused named local variable'
        int b = 2;
        // comment before unary
        a++;
        System.out.println(b);
    }

    public void orphanedFirstWithComment() {
        int a = 1; // violation 'Unused named local variable'
        // comment before orphaned first
        a = 10;
        System.out.println("done");
    }

    public void commentSameLineAsBlockStart() {
        { /* same line */ int a = 1; // violation 'Unused named local variable'
          System.out.println("after");
        }
    }

    public void multiplePendingCommentsDifferentWhitespace() {
        int keep = 1;
        // First comment
            // Second comment
        int a = 1; // violation 'Unused named local variable'
        System.out.println(keep);
    }

    public void blockCommentOnSameLine() {
        /* comment */ int a = 1; System.out.println("no newline"); // violation 'Unused named local variable'
    }

    public void multipleBlankLinesBeforeKept() {

        /* comment */ int a = 1; System.out.println("done"); // violation 'Unused named local variable'
    }
}
