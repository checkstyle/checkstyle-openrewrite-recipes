/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationLocationCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.annotationlocation.classannotation;

@Deprecated(since = "1.0") class InputClassAnnotation { // violation 'Annotation 'Deprecated' should be alone on line.'
}
