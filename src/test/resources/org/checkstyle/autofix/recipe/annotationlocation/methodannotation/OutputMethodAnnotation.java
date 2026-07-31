/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationLocationCheck">
      <property name="allowSamelineSingleParameterlessAnnotation" value="false"/>
    </module>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.annotationlocation.methodannotation;

public class OutputMethodAnnotation {
    @Deprecated
    void test() {
        @Deprecated
        class LocalClass {
        }
        int    a   =   1 ;
    }
}
