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

public class InputMethodNoModifier {

    @Target(ElementType.METHOD)
    @interface Ann {}

    // violation below 'Annotation 'Ann' should be on the same line with its target.'
    @Ann
    void methodNoModifierWithReturnType() {}

    // violation below 'Annotation 'Ann' should be on the same line with its target.'
    @Ann
    int methodNoModifierReturnsInt() {
        return 0;
    }

    // violation below 'Annotation 'Ann' should be on the same line with its target.'
    @Ann
    String methodNoModifierReturnsString() {
        return "";
    }
}
