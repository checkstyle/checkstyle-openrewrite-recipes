/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationOnSameLineCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.annotationonsameline.nochangemultispacing;

public class OutputNoChangeMultiSpacing {
    @interface Ann1 {}
    @interface Ann2 {}


    @Ann1  @Ann2 class Target {
    }
}
