/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationOnSameLineCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.annotationonsameline.nestedvariable;

public class OutputNestedVariable {

    @interface Ann {}

    Object obj = new Object() {
        @Ann public void innerMethod() {}
    };
}
