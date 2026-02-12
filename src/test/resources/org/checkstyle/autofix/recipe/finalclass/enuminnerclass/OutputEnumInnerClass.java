/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.design.FinalClassCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.finalclass.enuminnerclass;

enum OutputEnumInnerClass {
    A, B;

    public static final class SomeInnerClass {
        private SomeInnerClass() {}
    }
}
