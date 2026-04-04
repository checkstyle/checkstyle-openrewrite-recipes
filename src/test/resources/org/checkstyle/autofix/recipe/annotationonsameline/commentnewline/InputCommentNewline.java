/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationOnSameLineCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.annotationonsameline.commentnewline;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

public class InputCommentNewline {

    @Target({ElementType.METHOD, ElementType.FIELD})
    @interface Ann {}

    // violation below 'Annotation 'Ann' should be on the same line with its target.'
    @Ann /* c1 */
    /* c2 */ void methodWithMultiLineComment() {}

    // violation below 'Annotation 'Ann' should be on the same line with its target.'
    @Ann /* c3 */
    /* c4 */ int fieldWithBlockComment;
}
