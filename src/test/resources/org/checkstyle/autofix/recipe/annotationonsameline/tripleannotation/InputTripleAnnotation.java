/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationOnSameLineCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.annotationonsameline.tripleannotation;

public class InputTripleAnnotation {
    @interface A1 {}
    @interface A2 {}
    @interface A3 {}

    // violation below 'Annotation 'A1' should be on the same line with its target.'
    @A1
    // violation below 'Annotation 'A2' should be on the same line with its target.'
    @A2
    // violation below 'Annotation 'A3' should be on the same line with its target.'
    @A3
    class Target {
    }
}
