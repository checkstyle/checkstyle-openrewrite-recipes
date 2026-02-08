/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.design.FinalClassCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.finalclass.staticinnerclass;

class InputStaticInnerClass {

    InputStaticInnerClass() {}

    static class StaticInner { // violation 'Class StaticInner should be declared as final.'
        private StaticInner() {}
    }
}
