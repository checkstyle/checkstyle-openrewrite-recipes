/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationOnSameLineCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.annotationonsameline.methodnomodifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

public class OutputMethodNoModifier {

    @Target(ElementType.METHOD)
    @interface Ann {}

    @Ann void methodNoModifierWithReturnType() {}

    @Ann int methodNoModifierReturnsInt() {
        return 0;
    }

    @Ann String methodNoModifierReturnsString() {
        return "";
    }
}
