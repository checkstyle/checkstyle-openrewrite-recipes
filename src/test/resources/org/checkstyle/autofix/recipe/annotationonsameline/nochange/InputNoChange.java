/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationOnSameLineCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.annotationonsameline.nochange;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

public class InputNoChange {

    @Target({ElementType.METHOD, ElementType.TYPE, ElementType.FIELD, ElementType.TYPE_PARAMETER})
    @interface Ann {}

    @Ann public void alreadyOnSameLine() {}
    @Ann public int alreadyOnSameLineField;
    @Ann public class AlreadyOnSameLineClass {}
    @Ann @Deprecated public void multipleAlreadyOnSameLine() {}

    class TypeParamTest<@Ann T> {
        public void test() {}
    }
}
