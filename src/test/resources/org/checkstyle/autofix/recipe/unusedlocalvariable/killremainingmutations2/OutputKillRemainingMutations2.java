/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.killremainingmutations2;

public class OutputKillRemainingMutations2 {

    public void orphanedAssignmentFirstStatement() {
        if (true) {
            /* comment */
            System.out.println("next");
        }
    }

    public void multipleKeptStatements() {
        /* c1 */
        System.out.println("A");
        System.out.println("B");
    }

    public void firstStatementPendingCommentSpacing() {
        // comment for first statement
        System.out.println("first");
    }

    public void orphanedAssignmentFirstStatementSpacing() {
        getString().length();
        System.out.println("after");
    }

    public void pendingWhitespaceResetForPartialDeclaration() {
        // comment before kept declaration
        int keep = 2;
        System.out.println(keep);
        // comment before final statement
        System.out.println("done");
    }

    public void pendingWhitespaceResetForExtractedInitializer() {
        // comment before extracted initializer
        getString().length();
        System.out.println("middle");
        // comment before final statement
        System.out.println("done");
    }

    public void pendingWhitespaceResetForOrphanedAssignment() {
        // comment before orphaned assignment
        /* trailing */
        getString().length();
        System.out.println("middle");
        // comment before final statement
        System.out.println("done");
    }

    public void orphanedAssignmentAfterEmptyRemoval() {
        getString().length();
        System.out.println("after");
    }

    public void orphanedAssignmentWithTrailingTransfer() {
        System.out.println("keep"); /* trail */
        getString().length();
        System.out.println("after");
    }

    public void preserveFirstPendingWhitespaceAcrossRemovals() {
        int keep = 1;
        // first comment
        // second comment
        System.out.println("after");
        System.out.println(keep);
    }

    private String getString() {
        return "value";
    }
}
