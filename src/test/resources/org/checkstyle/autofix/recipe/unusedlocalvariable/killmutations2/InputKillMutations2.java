/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.killmutations2;

public class InputKillMutations2 {

    public void isolatedPreIncrement() {
        int dummy = 10;
        int unused = ++dummy; // violation 'Unused named local variable'
        System.out.println(dummy);
    }

    public void isolatedPreDecrement() {
        int dummy = 10;
        int unused = --dummy; // violation 'Unused named local variable'
        System.out.println(dummy);
    }

    public void isolatedPostIncrement() {
        int dummy = 10;
        int unused = dummy++; // violation 'Unused named local variable'
        System.out.println(dummy);
    }

    public void isolatedPostDecrement() {
        int dummy = 10;
        int unused = dummy--; // violation 'Unused named local variable'
        System.out.println(dummy);
    }

    public void partialNonStatementExpression() {
        boolean a = true;
        boolean u = (a = false); // violation 'Unused named local variable'
        System.out.println(a);
    }

    public void complexCommentTransfer() {
        /* lead */ int unused = getString().length(); // violation 'Unused named local variable'
        // trail
        System.out.println("done");
    }

    public void commentTransferToExtracted() {
        /* lead */ int unused = getString().length(); // violation 'Unused named local variable'
    }

    public void trailingTransferToExtracted() {
        int unused = getString().length(); /* trail */ // violation 'Unused named local variable'
        getString().length();
    }

    public void multipleStatementsAfterTrailingComment() {
        int unused = 1; /* trail */ // violation 'Unused named local variable'
        System.out.println("first");
        System.out.println("second");
    }

    public void multipleStatementsAfterTrailingCommentExtracted() {
        int unused = getString().length(); /* trail */ // violation 'Unused named local variable'
        System.out.println("first");
    }

    public void newStatementsEmptyKeepsBlankLine() {
        int a = 1; // violation 'Unused named local variable'

        int b = 2;
        int unused = 3; // violation 'Unused named local variable'

        System.out.println(b);
    }

    public void extractedInitializerKeepsBlankLine() {
        int a = 1; // violation 'Unused named local variable'

        int unused = getString().length(); // violation 'Unused named local variable'
        System.out.println("Wait");
    }

    public void stripLeadingBlankLinesTest() {
        int unused = 1; // violation 'Unused named local variable'

        System.out.println("Second");
    }

    public void commentMoveTest() {
        /* lead */ int unused = 2; // violation 'Unused named local variable'
        System.out.println("First");
    }

    public void leakingRemovedNames1() {
        int sharedName = 1; // violation 'Unused named local variable'
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
