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

package org.checkstyle.autofix.recipe.annotationlocation.integrationwithannotationonsameline;

@Deprecated interface OutputAnother {
}

public class OutputIntegrationWithAnnotationOnSameLine {

    @Deprecated
    public void method() {
    }

}
