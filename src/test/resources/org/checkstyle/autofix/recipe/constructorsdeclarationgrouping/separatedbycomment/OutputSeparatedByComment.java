/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.separatedbycomment;

/**
 * Class where constructors are separated only by comments.
 * Comments between constructors are allowed, so no violations should occur.
 */
public class OutputSeparatedByComment {

    OutputSeparatedByComment() {}

    // Default constructor above, parameterized below.
    OutputSeparatedByComment(String s) {}

    /* Another comment block. */
    OutputSeparatedByComment(int x) {}

}
