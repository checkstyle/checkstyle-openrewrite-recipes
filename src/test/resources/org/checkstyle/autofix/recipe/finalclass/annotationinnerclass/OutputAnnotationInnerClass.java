/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.design.FinalClassCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.finalclass.annotationinnerclass;

@interface OutputAnnotationInnerClass {

    final class SomeClass {
        private SomeClass() {}
    }
}
