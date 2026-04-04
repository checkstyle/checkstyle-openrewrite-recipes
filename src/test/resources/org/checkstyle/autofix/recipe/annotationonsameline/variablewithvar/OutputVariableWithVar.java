/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationOnSameLineCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.annotationonsameline.variablewithvar;

public class OutputVariableWithVar {
    @interface Ann {}

    class A {
        void m() {
            @Ann @SuppressWarnings("unused") var x = 1;
        }
    }
}
