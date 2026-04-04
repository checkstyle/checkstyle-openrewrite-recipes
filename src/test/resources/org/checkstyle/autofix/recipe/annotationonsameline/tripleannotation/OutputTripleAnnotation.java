/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationOnSameLineCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.annotationonsameline.tripleannotation;

public class OutputTripleAnnotation {
    @interface A1 {}
    @interface A2 {}
    @interface A3 {}

    @A1 @A2 @A3 class Target {
    }
}
