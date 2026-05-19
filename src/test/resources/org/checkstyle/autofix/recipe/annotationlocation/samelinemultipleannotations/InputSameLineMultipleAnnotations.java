/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationLocationCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.annotationlocation.samelinemultipleannotations;

public class InputSameLineMultipleAnnotations {
    @Deprecated @Override public int hashCode() { // violation 'Annotation 'Override' should be alone on line.'
        return 1;
    }
}
