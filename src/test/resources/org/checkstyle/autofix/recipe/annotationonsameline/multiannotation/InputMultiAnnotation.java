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

public class InputMultiAnnotation {

    @Target({ElementType.METHOD, ElementType.TYPE, ElementType.FIELD})
    @interface Ann1 {}

    @Target({ElementType.METHOD, ElementType.TYPE, ElementType.FIELD})
    @interface Ann2 {}

    @Target({ElementType.METHOD, ElementType.TYPE, ElementType.FIELD})
    @interface Ann3 {}

    // violation below 'Annotation 'Ann1' should be on the same line with its target.'
    @Ann1
    @Ann2 public void twoAnnMethod() {}

    // violation below 'Annotation 'Ann1' should be on the same line with its target.'
    @Ann1
    @Ann2 public int twoAnnField;

    // violation below 'Annotation 'Ann1' should be on the same line with its target.'
    @Ann1
    @Ann2 public class TwoAnnClass {}

    @Ann1 @Ann2 // 2 violations
    @Ann3 public void threeAnnMethod() {}

    @Ann1  @Ann2 public void noViolationCase() {}
}
