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

public class OutputCommentNewline {

    @Target({ElementType.METHOD, ElementType.FIELD})
    @interface Ann {}

    @Ann /* c1 */ /* c2 */ void methodWithMultiLineComment() {}

    @Ann /* c3 */ /* c4 */ int fieldWithBlockComment;
}
