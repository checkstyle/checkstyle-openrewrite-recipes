/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationOnSameLineCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.annotationonsameline.fieldnomodifiers;

public class OutputFieldNoModifiers {
    @interface Ann {}

    class A {
        @Ann int x;
    }
}
