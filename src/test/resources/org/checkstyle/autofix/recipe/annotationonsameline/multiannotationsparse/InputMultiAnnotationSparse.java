/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationOnSameLineCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.annotationonsameline.multiannotationsparse;

public class InputMultiAnnotationSparse {
    @interface Ann1 {}
    @interface Ann2 {}

    // violation below 'Annotation 'Ann1' should be on the same line with its target.'
    @Ann1
    // violation below 'Annotation 'Ann2' should be on the same line with its target.'
    @Ann2
    class Target {
    }
}
