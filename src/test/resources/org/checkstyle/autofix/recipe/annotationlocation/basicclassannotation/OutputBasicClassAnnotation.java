/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationLocationCheck">
      <property name="allowSamelineSingleParameterlessAnnotation" value="false"/>
    </module>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.annotationlocation.basicclassannotation;

@Deprecated
public class OutputBasicClassAnnotation {
    int    a   =   1 ;
}
