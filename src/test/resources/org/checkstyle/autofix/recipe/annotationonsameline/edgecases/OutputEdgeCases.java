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

public class OutputEdgeCases {

    @Target({ElementType.METHOD, ElementType.TYPE, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.LOCAL_VARIABLE})
    @interface Ann {}

    @Ann @Deprecated void method1() {
        @Ann int localVariable;
    }

    @Ann /* multi-line comment */ void method2() {}

    @Ann OutputEdgeCases() {}

    @Ann int fieldWithoutModifier;

    @Ann /* comment */ void methodWithNewlineInComment() {}

    @Ann static class InnerClassWithoutModifier {}
}
