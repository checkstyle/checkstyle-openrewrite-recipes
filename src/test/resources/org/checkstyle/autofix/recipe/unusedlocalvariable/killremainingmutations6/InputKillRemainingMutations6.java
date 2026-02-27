/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.killremainingmutations6;

public class InputKillRemainingMutations6 {

    public void ensureLeadingNewlineOnFirst() {
        /* comment */
            int a = getString().length(); // violation 'Unused named local variable'
    }

    public void multipleInlineRemoved() {
        int unused1 = 1; int unused2 = 2; // violation 'Unused named local variable'
        // violation above 'Unused named local variable'
        System.out.println("keep");
    }

    public void differentIndentation() {
        System.out.println("first");
        // violation below 'Unused named local variable'
        int unused = 1;

            System.out.println("keep");
    }

    public void partialSingleLineWithExtractedAndComment() {
        System.out.println("prefix"); /* comment 1 */
        /* comment 2 */ int keep = 1, unused = getString().length(); // violation 'Unused named local variable'
        System.out.println(keep);
    }

    public void blankLinePreservedWithComments() {
        System.out.println("first");

        // violation below 'Unused named local variable'
        int unused1 = 1;

        /* comment */
        // violation below 'Unused named local variable'
        int unused2 = 2; System.out.println("keep");
    }

    public void killLine245() {
        // violation 2 lines below 'Unused named local variable'
        // violation 2 lines below 'Unused named local variable'
        int unused1 = 1;
        int unused2 = 2;
        
            System.out.println("keep");
    }

    public void preserveCommentOnKeptVariable() {
        System.out.println("first"); /* comment */ int keep = 2, a = getString().length();
        System.out.println(keep);
        // violation 2 lines above 'Unused named local variable'
    }

    public void prependCommentOnSameLineWithoutNewline() {
        System.out.println("first"); /* comment */ int unused = 1; System.out.println("keep");
        // violation 1 lines above 'Unused named local variable'
    }

    public void consecutiveRemovedVariablesWithComment() {
        System.out.println("first");
        int unused1 = 1;
            /* comment 1 */ int unused2 = 2; System.out.println("keep");
            // violation 2 lines above 'Unused named local variable'
            // violation 2 lines above 'Unused named local variable'
    }

    public void clearPendingWhitespaceAfterConsumption() {
        System.out.println("first");
        int unused1 = 1;
            /* comment 1 */ int unused2 = 2; System.out.println("keep");
            // violation 2 lines above 'Unused named local variable'
            // violation 2 lines above 'Unused named local variable'
    }

    private String getString() {
        return "value";
    }
}
