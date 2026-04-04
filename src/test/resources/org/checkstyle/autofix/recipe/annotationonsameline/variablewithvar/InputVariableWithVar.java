/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationOnSameLineCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.annotationonsameline.variablewithvar;

public class InputVariableWithVar {
    @interface Ann {}

    class A {
        void m() {
            // violation below 'Annotation 'Ann' should be on the same line with its target.'
            @Ann
            // violation below 'Annotation 'SuppressWarnings' should be on the same line with its target.'
            @SuppressWarnings("unused")
            var x = 1;
        }
    }
}
