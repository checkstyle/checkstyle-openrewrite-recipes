/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationOnSameLineCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.annotationonsameline.multiannotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

public class OutputMultiAnnotation {

    @Target({ElementType.METHOD, ElementType.TYPE, ElementType.FIELD})
    @interface Ann1 {}

    @Target({ElementType.METHOD, ElementType.TYPE, ElementType.FIELD})
    @interface Ann2 {}

    @Target({ElementType.METHOD, ElementType.TYPE, ElementType.FIELD})
    @interface Ann3 {}

    @Ann1 @Ann2 public void twoAnnMethod() {}

    @Ann1 @Ann2 public int twoAnnField;

    @Ann1 @Ann2 public class TwoAnnClass {}

    @Ann1 @Ann2 @Ann3 public void threeAnnMethod() {}

    @Ann1  @Ann2 public void noViolationCase() {}
}
