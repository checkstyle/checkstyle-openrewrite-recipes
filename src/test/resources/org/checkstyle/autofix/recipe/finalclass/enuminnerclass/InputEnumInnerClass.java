/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.design.FinalClassCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.finalclass.enuminnerclass;

enum InputEnumInnerClass {
    A, B;

    public static class SomeInnerClass { // violation 'Class SomeInnerClass should be declared as final.'
        private SomeInnerClass() {}
    }
}
