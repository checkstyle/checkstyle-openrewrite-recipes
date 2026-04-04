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

public class OutputVariableWithModifier {

    @Target(ElementType.FIELD)
    @interface Ann {}

    @Ann private    int fieldWithPrivateModifier;

    @Ann protected    String fieldWithProtectedModifier;

    @Ann final    int fieldWithFinalModifier = 0;
}
