/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.killmutations5;

public class InputKillMutations5 {

    public void partialDeclarationExtractionWithBlankLine() {

        String keep = "1", unused = getString(); // violation 'Unused named local variable'
        System.out.println(keep);
    }

    public void partialDeclarationExtractionDifferentIndents() {
        System.out.println("");
        /* c */
        String keep = "1", unused = getString(); // violation 'Unused named local variable'
        System.out.println(keep);
    }

    public void partialDeclarationCommentWithBlankLine() {
        System.out.print(""); /* c */

        String keep = "1", u = getString(); // violation 'Unused named local variable'
        System.out.println(keep);
    }

    public void partialDeclarationCommentSuffixNoNewline() {
        System.out.print("");
        /* c */ String keep = "1", u = getString(); // violation 'Unused named local variable'
        System.out.println(keep);
    }

    public void precedingUnusedSameLinePartial() {
        System.out.println("keep");
        int unused1 = 1; // violation 'Unused named local variable'
        int keep = 2;
        int unused2 = 3; // violation 'Unused named local variable'
        System.out.println(keep);
    }

    public void blankLineBeforeRemoved() {
        System.out.println("first");

        int unused = 1; // violation 'Unused named local variable'
        System.out.println("keep");
    }

    private String getString() {
        return "value";
    }
}
