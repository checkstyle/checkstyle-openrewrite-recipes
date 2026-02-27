/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.killremainingmutations6;

public class OutputKillRemainingMutations6 {

    public void ensureLeadingNewlineOnFirst() {
            /* comment */
            getString().length();
    }

    public void multipleInlineRemoved() {
        System.out.println("keep");
    }

    public void differentIndentation() {
        System.out.println("first");
            System.out.println("keep");
    }

    public void partialSingleLineWithExtractedAndComment() {
        System.out.println("prefix"); /* comment 1 */
        /* comment 2 */ int keep = 1;
        getString().length();
        System.out.println(keep);
    }

    public void blankLinePreservedWithComments() {
        System.out.println("first");

        /* comment */
        System.out.println("keep");
    }

    public void killLine245() {
            System.out.println("keep");
    }

    public void preserveCommentOnKeptVariable() {
        System.out.println("first"); /* comment */ int keep = 2;
getString().length();
        System.out.println(keep);
    }

    public void prependCommentOnSameLineWithoutNewline() {
        System.out.println("first"); /* comment */ System.out.println("keep");
    }

    public void consecutiveRemovedVariablesWithComment() {
        System.out.println("first");
        /* comment 1 */ System.out.println("keep");
    }

    public void clearPendingWhitespaceAfterConsumption() {
        System.out.println("first");
        /* comment 1 */ System.out.println("keep");
    }

    private String getString() {
        return "value";
    }
}
