/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.killmutations3;

public class OutputKillMutations3 {

    public void doubleParenthesizedInitializer() {
        int x = 1;
        x = 2;
        System.out.println(x);
    }

    public void stripLeadingBlankLinesTest2() {
        int b = 2;
        System.out.println(b);
    }

    public void keepDeclarationStripTest() {
        int keep = 2;
        System.out.println(keep);
    }

    public void extractInitializerStripTest() {
        getString().length();
    }

    public void partialDeclarationsSetEmptyFlag() {
        int a = 1;

        System.out.println(a);

        int b = 3;
        System.out.println(b);
    }

    public void partialWithSideEffect() {
        int a = 1;
        getString().length();
        System.out.println(a);
    }

    public void trailingCommentBeforeExtracted() {
        /* trailing */
        getString().length();
    }

    public void partialWithTrailingComment() {
        int keep = 1; /* trailing */
        System.out.println(keep);
    }

    public void partialWithTrailingCommentAndExtracted() {
        int keep = 1; /* trailing */
        getString().length();
        System.out.println(keep);
    }

    public void partialKeepsBlankLine() {
        int keep = 1;

        int keep2 = 3;
        System.out.println(keep);
        System.out.println(keep2);
    }

    public void orphanedKeepsBlankLine() {
        getString().length();

        System.out.println("next");
    }

    public void firstStatementStripTest() {

        getString().length();
        System.out.println("Wait");
    }

    public void leakingRemovedNamesAnonymous() {
        Runnable r = new Runnable() {
            @Override
            public void run() {
            }
        };
        r.run();
    }

    private String getString() {
        return "value";
    }
}
