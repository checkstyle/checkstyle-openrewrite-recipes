/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationOnSameLineCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.annotationonsameline.variablewithmodifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

public class InputVariableWithModifier {

    @Target(ElementType.FIELD)
    @interface Ann {}

    // violation below 'Annotation 'Ann' should be on the same line with its target.'
    @Ann
    private    int fieldWithPrivateModifier;

    // violation below 'Annotation 'Ann' should be on the same line with its target.'
    @Ann
    protected    String fieldWithProtectedModifier;

    // violation below 'Annotation 'Ann' should be on the same line with its target.'
    @Ann
    final    int fieldWithFinalModifier = 0;
}
