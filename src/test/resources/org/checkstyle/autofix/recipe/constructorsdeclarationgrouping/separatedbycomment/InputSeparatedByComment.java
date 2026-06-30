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
public class InputSeparatedByComment {

    InputSeparatedByComment() {}

    // Default constructor above, parameterized below.
    InputSeparatedByComment(String s) {}

    /* Another comment block. */
    InputSeparatedByComment(int x) {}

}
