/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationOnSameLineCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.annotationonsameline.methodannotation;

public class InputMethodAnnotation {

    @SuppressWarnings("unchecked") // violation, "should be on the same line with its target."
    public    void foo() {
    }
}
