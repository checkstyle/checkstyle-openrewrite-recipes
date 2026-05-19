/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationLocationCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.annotationlocation.methodannotation;

public class InputMethodAnnotation {
    @Deprecated(since = "1.0") public int foo() { // violation 'Annotation 'Deprecated' should be alone on line.'
        return 1;
    }
}
