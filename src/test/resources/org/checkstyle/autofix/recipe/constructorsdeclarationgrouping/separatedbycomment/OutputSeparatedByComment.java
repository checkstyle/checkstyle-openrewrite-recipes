/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.separatedbycomment;

/**
 * Constructors are separated only by comments (single-line and block).
 * According to the rule, comments between constructors are allowed.
 * Verifies that no modification is made.
 */
public class OutputSeparatedByComment {

    OutputSeparatedByComment() {}

    // Default constructor above, parameterized below.
    OutputSeparatedByComment(String s) {}

    /* Another comment block. */
    OutputSeparatedByComment(int x) {}

}
