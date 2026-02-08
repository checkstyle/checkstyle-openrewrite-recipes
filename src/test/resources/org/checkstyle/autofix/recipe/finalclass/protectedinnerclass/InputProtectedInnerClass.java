/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.design.FinalClassCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.finalclass.protectedinnerclass;

class InputProtectedInnerClass {

    InputProtectedInnerClass() {}

    protected class Inner { // violation 'Class Inner should be declared as final.'
        private Inner() {
        }
    }
}
