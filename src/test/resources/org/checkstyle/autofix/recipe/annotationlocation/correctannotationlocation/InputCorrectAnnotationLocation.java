/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationLocationCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.annotationlocation.correctannotationlocation;

public class InputCorrectAnnotationLocation {
    @Deprecated
    private boolean field1; // ok

    @Override
    public int hashCode() { // ok
        return 1;
    }
}
