/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.killmutations;

public class InputKillMutations {

    public void unaryTest() {
        int dummy = 10;
        dummy++;
        ++dummy;
        dummy--;
        --dummy;
        int unused = 1; // violation 'Unused named local variable'

        System.out.println(dummy);
    }

    public void assignmentTest() {
        int dummy = 10;
        dummy += 5;
        dummy = 20;

        int assignOp = 0;
        assignOp += 5;
        int unused = 1; // violation 'Unused named local variable'

        System.out.println(dummy);
        System.out.println(assignOp);
    }

    public void noInitTest() {
        int unused = 1; // violation 'Unused named local variable'
    }

    public void mixedBlockTest() {
        int unused = 1; // violation 'Unused named local variable'
        System.out.println("Hello");

        Runnable r = new Runnable() {
            @Override
            public void run() {
                int unusedInner = 2; // violation 'Unused named local variable'
            }
        };
        r.run();
    }

    public void orphanedWithComment() {
        // comment on orphaned
        int unused = 1; // violation 'Unused named local variable'
        System.out.println("after");
    }

    public void trailingCommentOnRemovedVar() {
        int unused = 1; /* trailing */ // violation 'Unused named local variable'
        System.out.println("end");
    }

    public void parenthesizedInitializer() {
        int dummy = 10;
        dummy++;
        int unused = (dummy = 20); // violation 'Unused named local variable'
        getString();
        System.out.println(dummy);
    }

    public void partialDeclarationExtraction() {
        String keep = "1", unused = getString(); // violation 'Unused named local variable'
        System.out.println(keep);
    }

    public void partialDeclarationNoExtraction() {
        int k = 1, u = 2; // violation 'Unused named local variable'
        System.out.println(k);
    }

    public void trailingCommentCoverage() {
        int u = getString().length(); /* trail */ // violation 'Unused named local variable'
        int u2 = getString().length(); // violation 'Unused named local variable'
        System.out.println("after");
    }

    public void parenthesizedNonStatementUnaries() {
        int x = 0;
        int u1 = (x++); // violation 'Unused named local variable'
        int u2 = (++x); // violation 'Unused named local variable'
        int u3 = (x--); // violation 'Unused named local variable'
        int u4 = (--x); // violation 'Unused named local variable'
        System.out.println(x);
    }

    public void commentOnExtractedInitializer() {
        // comment above removed
        int unused = getString().length(); // violation 'Unused named local variable'
        // comment on extracted
        int unused2 = getString().length(); // violation 'Unused named local variable'
        System.out.println("done");
    }

    private String getString() {
        return "value";
    }
}
