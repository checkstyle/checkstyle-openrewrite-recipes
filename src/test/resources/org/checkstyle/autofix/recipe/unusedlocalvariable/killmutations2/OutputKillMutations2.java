/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.killmutations2;

public class OutputKillMutations2 {

    public void isolatedPreIncrement() {
        int dummy = 10;
        ++dummy;
        System.out.println(dummy);
    }

    public void isolatedPreDecrement() {
        int dummy = 10;
        --dummy;
        System.out.println(dummy);
    }

    public void isolatedPostIncrement() {
        int dummy = 10;
        dummy++;
        System.out.println(dummy);
    }

    public void isolatedPostDecrement() {
        int dummy = 10;
        dummy--;
        System.out.println(dummy);
    }

    public void partialNonStatementExpression() {
        boolean a = true;
        a = false;
        System.out.println(a);
    }

    public void complexCommentTransfer() {
        /* lead */ getString().length();
        // trail
        System.out.println("done");
    }

    public void commentTransferToExtracted() {
        /* lead */ getString().length();
    }

    public void trailingTransferToExtracted() {
        getString().length(); /* trail */
        getString().length();
    }

    public void multipleStatementsAfterTrailingComment() {
        /* trail */
        System.out.println("first");
        System.out.println("second");
    }

    public void multipleStatementsAfterTrailingCommentExtracted() {
        getString().length(); /* trail */
        System.out.println("first");
    }

    public void newStatementsEmptyKeepsBlankLine() {
        int b = 2;

        System.out.println(b);
    }

    public void extractedInitializerKeepsBlankLine() {
        getString().length();
        System.out.println("Wait");
    }

    public void stripLeadingBlankLinesTest() {
        System.out.println("Second");
    }

    public void commentMoveTest() {
        /* lead */
        System.out.println("First");
    }

    public void leakingRemovedNames1() {
    }

    public void leakingRemovedNames2() {
        int sharedName = 10;
        sharedName = 20;
        System.out.println(sharedName);
    }

    private String getString() {
        return "value";
    }
}
