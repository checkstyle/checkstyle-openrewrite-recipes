/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.killtransfercommentmutations;

public class OutputKillTransferCommentMutations {

    public void leadingCommentBeforeOrphaned() {
        int b = 2;
        // comment before orphaned
        System.out.println(b);
    }

    public void trailingCommentBeforeOrphaned() {
        int b = 2; /* same line comment */
        System.out.println(b);
    }

    public void multipleCommentsFlipTrailing() {
        int b = 2; /* trailing c1 */
        /* leading c2 */
        System.out.println(b);
    }

    public void commentBeforeOrphanedUnary() {
        int b = 2;
        // comment before unary
        System.out.println(b);
    }

    public void orphanedFirstWithComment() {
        // comment before orphaned first
        System.out.println("done");
    }

    public void commentSameLineAsBlockStart() {
        {
          /* same line */
          System.out.println("after");
        }
    }

    public void multiplePendingCommentsDifferentWhitespace() {
        int keep = 1;
        // First comment
            // Second comment
        System.out.println(keep);
    }

    public void blockCommentOnSameLine() {
        /* comment */ System.out.println("no newline");
    }

    public void multipleBlankLinesBeforeKept() {
        /* comment */ System.out.println("done");
    }
}
