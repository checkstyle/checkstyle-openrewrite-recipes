/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.design.FinalClassCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.finalclass.innerclass;

class InputInnerClass {

    InputInnerClass() {}

    class InnerWithPrivateCtor { // violation 'Class InnerWithPrivateCtor should be declared as final.'
        private InnerWithPrivateCtor() {}
    }
}
