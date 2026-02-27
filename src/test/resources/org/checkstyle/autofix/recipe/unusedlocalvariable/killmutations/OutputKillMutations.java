/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.killmutations;

public class OutputKillMutations {

    public void unaryTest() {
        int dummy = 10;
        dummy++;
        ++dummy;
        dummy--;
        --dummy;

        System.out.println(dummy);
    }

    public void assignmentTest() {
        int dummy = 10;
        dummy += 5;
        dummy = 20;

        int assignOp = 0;
        assignOp += 5;

        System.out.println(dummy);
        System.out.println(assignOp);
    }

    public void noInitTest() {
    }

    public void mixedBlockTest() {
        System.out.println("Hello");

        Runnable r = new Runnable() {
            @Override
            public void run() {
            }
        };
        r.run();
    }

    public void orphanedWithComment() {
        // comment on orphaned
        System.out.println("after");
    }

    public void trailingCommentOnRemovedVar() {
        /* trailing */
        System.out.println("end");
    }

    public void parenthesizedInitializer() {
        int dummy = 10;
        dummy++;
        dummy = 20;
        getString();
        System.out.println(dummy);
    }

    public void partialDeclarationExtraction() {
        String keep = "1";
        getString();
        System.out.println(keep);
    }

    public void partialDeclarationNoExtraction() {
        int k = 1;
        System.out.println(k);
    }

    public void trailingCommentCoverage() {
        getString().length(); /* trail */
        getString().length();
        System.out.println("after");
    }

    public void parenthesizedNonStatementUnaries() {
        int x = 0;
        x++;
        ++x;
        x--;
        --x;
        System.out.println(x);
    }

    public void commentOnExtractedInitializer() {
        // comment above removed
        getString().length();
        // comment on extracted
        getString().length();
        System.out.println("done");
    }

    private String getString() {
        return "value";
    }
}
