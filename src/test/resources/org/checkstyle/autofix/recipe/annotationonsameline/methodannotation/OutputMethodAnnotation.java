/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationOnSameLineCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.annotationonsameline.methodannotation;

public class OutputMethodAnnotation {

    @SuppressWarnings("unchecked") public    void foo() {
    }
}
