/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.killremainingmutations4;

public class InputKillRemainingMutations4 {

    public void pendingCommentWithExtraBlankLine() {
        // c1

        int unused = 1; // violation 'Unused named local variable'
        System.out.println("done");
    }

    public void carryOverWhitespaceToInitializer() {
        int a = 0;

        int unused = 1; // violation 'Unused named local variable'
        int kept = ++a; // violation 'Unused named local variable'
        System.out.println(a);
    }

    public void carryOverWhitespaceToOrphanedAssignment() {
        int a = 0;
        int orphan = 0; // violation 'Unused named local variable'

        int unused = 1; // violation 'Unused named local variable'
        orphan = ++a;
        System.out.println(a);
    }

    public void inlineCommentBeforeStatement() {
        /* comment */ int unused = 1; // violation 'Unused named local variable'
        System.out.println("after");
    }

    public void multipleBlankLines() {


        int unused = 1; // violation 'Unused named local variable'
        System.out.println("after");
    }

    public void multipleBlankLinesOnKept() {
        int unused = 1; // violation 'Unused named local variable'

        System.out.println("after");
    }

    public void commentWithNewline() {
        /* comment */
        int unused = 1; // violation 'Unused named local variable'
        System.out.println("after");
    }

    public void sameLineKeptFirst() {
        /* comment */ int unused = 1; // violation 'Unused named local variable'
        System.out.println("after");
    }

    public void newlineInInitializer() {
        int a = 0;
        int unused = // violation 'Unused named local variable'
            ++a;
        System.out.println(a);
    }

    public void preserveFirstWhitespaceMultiple() {

        int u1 = 1; // violation 'Unused named local variable'


        int u2 = 2; // violation 'Unused named local variable'
        int used = 3;
        System.out.println(used);
    }

    public void killUnclearedPendingWhitespace() {
        int a = 0;
        int unused = 1; // violation 'Unused named local variable'
        System.out.println("hello");


        unused = ++a;
        System.out.println(a);
    }

    public void multipleBlankLinesOnRemoved() {


        int unused = 1; // violation 'Unused named local variable'
        System.out.println("after");
    }

    private String getString() {
        return "value";
    }
}
