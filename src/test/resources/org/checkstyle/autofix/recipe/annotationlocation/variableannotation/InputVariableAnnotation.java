/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationLocationCheck">
      <property name="allowSamelineSingleParameterlessAnnotation" value="false"/>
    </module>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.annotationlocation.variableannotation;

public class InputVariableAnnotation {
    @Deprecated int test   ; // violation "Annotation 'Deprecated' should be alone on line."

    @Deprecated Object obj = new Object() { // violation "Annotation 'Deprecated' should be alone on line."
        @Deprecated void innerMethod() {} // violation "Annotation 'Deprecated' should be alone on line."
    };
}
