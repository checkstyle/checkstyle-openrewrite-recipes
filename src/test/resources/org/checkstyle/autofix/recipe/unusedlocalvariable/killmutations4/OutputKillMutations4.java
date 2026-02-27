/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.killmutations4;

public class OutputKillMutations4 {

    public void singleLineBlock() {
        int keep = 2;
        System.out.println(keep);
    }

    public void statementExpressionTest() {
        boolean flag = true;

        int keep = 1;
        System.out.println(keep);
        System.out.println(flag);
    }

    public void trailingCommentBeforeExtractedWithStatements() {
        /* trailing */
        getString().length();
        System.out.println("Wait");
    }

    public void orphanedAssignmentStripTest() {
        System.out.println("Wait");
    }

    public void trailingCommentWithMultipleStatements() {
        /* trailing */
        System.out.println("stmt1");
        System.out.println("stmt2");
    }

    public void precedingStatementWithTrailingComment() {
        System.out.println("keep"); /* trail */
        System.out.println("first");
        System.out.println("second");
    }

    public void precedingStatementWithTrailingCommentAndExtracted() {
        System.out.println("keep"); /* trail */
        getString().length();
        System.out.println("end");
    }

    public void inlineTrailingCommentSingleLineBlock() {
        /* c */ System.out.println("test");
    }

    public void consecutiveUnusedSameLineTrailing() {
        /* trail */
        System.out.println("after");
    }

    public void entirelyRemovedWithComment() {
        // pending comment
        System.out.println("after");
    }

    public void partialDeclarationNoNewline() {
        System.out.print("");
        String keep = "1";
        getString();
        System.out.println(keep);
    }

    public void partialDeclarationCommentNoNewline() {
        System.out.print(""); /* c */
        String keep = "1";
        getString();
        System.out.println(keep);
    }

    public void extractMultipleInitializers() {
        getString().length(); /* c */ getString().length();
    }

    public void trailingCommentWithSameLineNextStatement() {
        System.out.println("first");
        /* trail */ System.out.println("kept");
    }

    private String getString() {
        return "value";
    }
}
