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

public class OutputClassNoModifiers {

    @Target(ElementType.TYPE)
    @interface Ann {}

    @Ann class InnerNoModifiers {}

    @Deprecated public    class InnerWithModifiers {}
}
