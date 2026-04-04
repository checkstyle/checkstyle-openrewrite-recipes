/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationOnSameLineCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.annotationonsameline.nestedvariable;

public class InputNestedVariable {

    @interface Ann {}

    Object obj = new Object() {
        // violation below 'Annotation 'Ann' should be on the same line with its target.'
        @Ann
        public void innerMethod() {}
    };
}
