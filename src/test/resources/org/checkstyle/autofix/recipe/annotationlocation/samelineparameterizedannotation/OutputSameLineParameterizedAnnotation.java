/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationLocationCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.annotationlocation.samelineparameterizedannotation;

public class OutputSameLineParameterizedAnnotation {
    @Deprecated(since = "1.0")
    Object loader;
}
