/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationOnSameLineCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.annotationonsameline.classnomodifiers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

public class InputClassNoModifiers {

    @Target(ElementType.TYPE)
    @interface Ann {}

    // violation below 'Annotation 'Ann' should be on the same line with its target.'
    @Ann
    class InnerNoModifiers {}

    // violation below 'Annotation 'Deprecated' should be on the same line with its target.'
    @Deprecated
    public    class InnerWithModifiers {}
}
