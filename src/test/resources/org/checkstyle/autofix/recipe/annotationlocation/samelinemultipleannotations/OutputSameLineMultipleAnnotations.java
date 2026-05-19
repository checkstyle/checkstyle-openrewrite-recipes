/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationLocationCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.annotationlocation.samelinemultipleannotations;

public class OutputSameLineMultipleAnnotations {
    @Deprecated
    @Override
    public int hashCode() {
        return 1;
    }
}
