/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.killremainingmutations3;

public class OutputKillRemainingMutations3 {

    public void attachPendingWithoutStrippingBlankLines() {
        int keep = 1;

        // comment for kept declaration

        int kept = 2;
        System.out.println(keep);
        System.out.println(kept);
    }

    public void stalePendingWhitespaceDoesNotLeakIntoOrphanedAssignment() {
        // comment before kept declaration
        int keep = 2;
        getString().length();
        System.out.println(keep);
    }

    public void stalePendingWhitespaceDoesNotLeakIntoExtractedInitializer() {
        // comment before kept declaration
        int keep = 2;
        getString().length();
        System.out.println(keep);
    }

    public void pendingWhitespaceAppliedToPlainStatement() {
        int keep = 1;

        // comment for plain statement

        System.out.println(keep);
        System.out.println("after");
    }

    public void inlineCommentToKeptDeclarationUsesIndent() {
        if (true) {
            /* lead */
            int keep = 2;
            System.out.println(keep);
        }
    }

    public void inlineCommentToExtractedInitializerUsesIndent() {
        if (true) {
            /* lead */
            getString().length();
            System.out.println("after");
        }
    }

    public void inlineCommentToOrphanedAssignmentUsesIndent() {
        if (true) {
            /* lead */
            getString().length();
            System.out.println("after");
        }
    }

    public void consecutiveExtractedInitializersWithPendingWhitespace() {
        int a = 0;
        // comment before completely removed
        ++a;
        ++a;
        System.out.println("done");
        System.out.println(a);
    }

    public void consecutiveOrphanedAssignmentsWithPendingWhitespace() {
        int a = 0;
        // comment before completely removed
        ++a;
        ++a;
        System.out.println("done");
        System.out.println(a);
    }

    private String getString() {
        return "value";
    }
}
