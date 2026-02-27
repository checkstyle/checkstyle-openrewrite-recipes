/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.killremainingmutations4;

public class OutputKillRemainingMutations4 {

    public void pendingCommentWithExtraBlankLine() {
        // c1
        System.out.println("done");
    }

    public void carryOverWhitespaceToInitializer() {
        int a = 0;
        ++a;
        System.out.println(a);
    }

    public void carryOverWhitespaceToOrphanedAssignment() {
        int a = 0;
        ++a;
        System.out.println(a);
    }

    public void inlineCommentBeforeStatement() {
        /* comment */
        System.out.println("after");
    }

    public void multipleBlankLines() {
        System.out.println("after");
    }

    public void multipleBlankLinesOnKept() {
        System.out.println("after");
    }

    public void commentWithNewline() {
        /* comment */
        System.out.println("after");
    }

    public void sameLineKeptFirst() {
        /* comment */
        System.out.println("after");
    }

    public void newlineInInitializer() {
        int a = 0;
        ++a;
        System.out.println(a);
    }

    public void preserveFirstWhitespaceMultiple() {
        int used = 3;
        System.out.println(used);
    }

    public void killUnclearedPendingWhitespace() {
        int a = 0;
        System.out.println("hello");


        ++a;
        System.out.println(a);
    }

    public void multipleBlankLinesOnRemoved() {
        System.out.println("after");
    }

    private String getString() {
        return "value";
    }
}
