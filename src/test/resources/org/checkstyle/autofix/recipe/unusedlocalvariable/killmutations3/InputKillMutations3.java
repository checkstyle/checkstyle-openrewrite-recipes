/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.killmutations3;

public class InputKillMutations3 {

    public void doubleParenthesizedInitializer() {
        int x = 1;
        int unused = ((x = 2)); // violation 'Unused named local variable'
        System.out.println(x);
    }

    public void stripLeadingBlankLinesTest2() {
        int unused = 1; // violation 'Unused named local variable'

        int b = 2;
        System.out.println(b);
    }

    public void keepDeclarationStripTest() {
        int unused = 1; // violation 'Unused named local variable'

        int keep = 2, unused2 = 3; // violation 'Unused named local variable'
        System.out.println(keep);
    }

    public void extractInitializerStripTest() {
        int unused = 1; // violation 'Unused named local variable'

        int unused2 = getString().length(); // violation 'Unused named local variable'
    }

    public void partialDeclarationsSetEmptyFlag() {
        int a = 1, u = 2; // violation 'Unused named local variable'

        System.out.println(a);

        int b = 3, u2 = 4; // violation 'Unused named local variable'
        System.out.println(b);
    }

    public void partialWithSideEffect() {
        int a = 1, u = ((getString().length())); // violation 'Unused named local variable'
        System.out.println(a);
    }

    public void trailingCommentBeforeExtracted() {
        int unused1 = 1; /* trailing */ // violation 'Unused named local variable'
        int unused2 = getString().length(); // violation 'Unused named local variable'
    }

    public void partialWithTrailingComment() {
        int keep = 1; int unused = 2; /* trailing */ // violation 'Unused named local variable'
        System.out.println(keep);
    }

    public void partialWithTrailingCommentAndExtracted() {
        int keep = 1; int unused1 = 2; /* trailing */ // violation 'Unused named local variable'
        int unused2 = getString().length(); // violation 'Unused named local variable'
        System.out.println(keep);
    }

    public void partialKeepsBlankLine() {
        int keep = 1;
        int unused = 2; // violation 'Unused named local variable'

        int keep2 = 3;
        System.out.println(keep);
        System.out.println(keep2);
    }

    public void orphanedKeepsBlankLine() {
        int sharedName = 10; // violation 'Unused named local variable'
        sharedName = getString().length();

        System.out.println("next");
    }

    public void firstStatementStripTest() {

        int unused = getString().length(); // violation 'Unused named local variable'
        System.out.println("Wait");
    }

    public void leakingRemovedNamesAnonymous() {
        int outerVar = 10; // violation 'Unused named local variable'
        Runnable r = new Runnable() {
            @Override
            public void run() {
                int innerVar = 1; // violation 'Unused named local variable'
            }
        };
        outerVar = 20;
        r.run();
    }

    private String getString() {
        return "value";
    }
}
