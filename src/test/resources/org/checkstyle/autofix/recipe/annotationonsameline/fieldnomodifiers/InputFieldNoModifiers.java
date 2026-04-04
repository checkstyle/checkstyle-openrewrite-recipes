/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationOnSameLineCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.annotationonsameline.fieldnomodifiers;

public class InputFieldNoModifiers {
    @interface Ann {}

    class A {
        // violation below 'Annotation 'Ann' should be on the same line with its target.'
        @Ann
        int x;
    }
}
