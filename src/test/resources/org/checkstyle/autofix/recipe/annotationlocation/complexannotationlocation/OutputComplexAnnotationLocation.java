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

@Deprecated
@SuppressWarnings("unchecked")
class OutputComplexAnnotationLocation {

    @Deprecated
    public void testModifiers() {}

    @Deprecated
    OutputComplexAnnotationLocation() {}

    @Deprecated
    public int testVarModifiers;

    @Deprecated
    void testNoModifiers() {}

    @Deprecated
    int testNoVarModifiers;

    @Deprecated
    public static void multiModMethod() {}

    @Deprecated
    public static int multiModVar;

    @Deprecated
    public    void   alreadyCorrect() {}
}
