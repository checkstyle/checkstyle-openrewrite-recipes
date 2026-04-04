/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationOnSameLineCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.annotationonsameline.edgecases;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

public class InputEdgeCases {

    @Target({ElementType.METHOD, ElementType.TYPE, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.LOCAL_VARIABLE})
    @interface Ann {}

    // violation below 'Annotation 'Ann' should be on the same line with its target.'
    @Ann
    @Deprecated // violation 'Annotation 'Deprecated' should be on the same line with its target.'
    void method1() {
        // violation below 'Annotation 'Ann' should be on the same line with its target.'
        @Ann
        int localVariable;
    }

    // violation below 'Annotation 'Ann' should be on the same line with its target.'
    @Ann
    /* multi-line comment */
    void method2() {}

    // violation below 'Annotation 'Ann' should be on the same line with its target.'
    @Ann
    InputEdgeCases() {}

    // violation below 'Annotation 'Ann' should be on the same line with its target.'
    @Ann
    int fieldWithoutModifier;

    // violation below 'Annotation 'Ann' should be on the same line with its target.'
    @Ann /* comment */
    void methodWithNewlineInComment() {}

    // violation below 'Annotation 'Ann' should be on the same line with its target.'
    @Ann
    static class InnerClassWithoutModifier {}
}
