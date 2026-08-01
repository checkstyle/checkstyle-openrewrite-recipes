/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationLocationCheck">
      <property name="allowSamelineSingleParameterlessAnnotation" value="false"/>
    </module>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.annotationlocation.complexannotationlocation;

@Deprecated @SuppressWarnings("unchecked") // violation "Annotation 'Deprecated' should be alone on line."
// violation above "Annotation 'SuppressWarnings' should be alone on line."
class InputComplexAnnotationLocation {

    @Deprecated public void testModifiers() {} // violation "Annotation 'Deprecated' should be alone on line."

    @Deprecated InputComplexAnnotationLocation() {} // violation "Annotation 'Deprecated' should be alone on line."

    @Deprecated public int testVarModifiers; // violation "Annotation 'Deprecated' should be alone on line."

    @Deprecated void testNoModifiers() {} // violation "Annotation 'Deprecated' should be alone on line."

    @Deprecated int testNoVarModifiers; // violation "Annotation 'Deprecated' should be alone on line."

    @Deprecated public static void multiModMethod() {} // violation "Annotation 'Deprecated' should be alone on line."

    @Deprecated public static int multiModVar; // violation "Annotation 'Deprecated' should be alone on line."

    @Deprecated
    public    void   alreadyCorrect() {}
}
