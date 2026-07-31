/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationOnSameLineCheck">
      <property name="tokens" value="INTERFACE_DEF"/>
    </module>
    <module name="com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationLocationCheck">
      <property name="allowSamelineSingleParameterlessAnnotation" value="false"/>
      <property name="tokens" value="METHOD_DEF"/>
    </module>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.annotationonsameline.integrationwithannotationlocation;

public class InputIntegrationWithAnnotationLocation {

    @Deprecated public void method() { // violation "Annotation 'Deprecated' should be alone on line."
    }

}

@Deprecated // violation "Annotation 'Deprecated' should be on the same line with its target."
interface InputAnother {
}
