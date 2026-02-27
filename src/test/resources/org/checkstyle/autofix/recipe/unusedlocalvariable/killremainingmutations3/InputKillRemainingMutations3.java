/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.killremainingmutations3;

public class InputKillRemainingMutations3 {

    public void attachPendingWithoutStrippingBlankLines() {
        int keep = 1;

        // comment for kept declaration
        int unused = 1; // violation 'Unused named local variable'

        int kept = 2, drop = 3; // violation 'Unused named local variable'
        System.out.println(keep);
        System.out.println(kept);
    }

    public void stalePendingWhitespaceDoesNotLeakIntoOrphanedAssignment() {

        // comment before kept declaration
        int unused = 1; // violation 'Unused named local variable'
        int keep = 2, drop = 3; // violation 'Unused named local variable'
        int orphan = 0; // violation 'Unused named local variable'
        orphan = getString().length();
        System.out.println(keep);
    }

    public void stalePendingWhitespaceDoesNotLeakIntoExtractedInitializer() {

        // comment before kept declaration
        int unused = 1; // violation 'Unused named local variable'
        int keep = 2, drop = 3; // violation 'Unused named local variable'
        int unused2 = getString().length(); // violation 'Unused named local variable'
        System.out.println(keep);
    }

    public void pendingWhitespaceAppliedToPlainStatement() {
        int keep = 1;

        // comment for plain statement
        int unused = 2; // violation 'Unused named local variable'

        System.out.println(keep);
        System.out.println("after");
    }

    public void inlineCommentToKeptDeclarationUsesIndent() {
        if (true) {
            /* lead */ int unused = 1; // violation 'Unused named local variable'
            int keep = 2, drop = 3; // violation 'Unused named local variable'
            System.out.println(keep);
        }
    }

    public void inlineCommentToExtractedInitializerUsesIndent() {
        if (true) {
            /* lead */ int unused = 1; // violation 'Unused named local variable'
            int unused2 = getString().length(); // violation 'Unused named local variable'
            System.out.println("after");
        }
    }

    public void inlineCommentToOrphanedAssignmentUsesIndent() {
        if (true) {
            /* lead */ int orphan = 0; // violation 'Unused named local variable'
            orphan = getString().length();
            System.out.println("after");
        }
    }

    public void consecutiveExtractedInitializersWithPendingWhitespace() {
        int a = 0;
        // comment before completely removed
        int unused = 1; // violation 'Unused named local variable'
        int unused2 = ++a; // violation 'Unused named local variable'
        int unused3 = ++a; // violation 'Unused named local variable'
        System.out.println("done");
        System.out.println(a);
    }

    public void consecutiveOrphanedAssignmentsWithPendingWhitespace() {
        int a = 0;
        int orphan1 = 0; // violation 'Unused named local variable'
        int orphan2 = 0; // violation 'Unused named local variable'

        // comment before completely removed
        int unused = 1; // violation 'Unused named local variable'
        orphan1 = ++a;
        orphan2 = ++a;
        System.out.println("done");
        System.out.println(a);
    }

    private String getString() {
        return "value";
    }
}
