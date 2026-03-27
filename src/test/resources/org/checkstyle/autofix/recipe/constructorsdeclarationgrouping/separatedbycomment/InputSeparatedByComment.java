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
public class InputSeparatedByComment {

    InputSeparatedByComment() {}

    // Default constructor above, parameterized below.
    InputSeparatedByComment(String s) {}

    /* Another comment block. */
    InputSeparatedByComment(int x) {}

}
