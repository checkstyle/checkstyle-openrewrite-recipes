/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.design.FinalClassCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.finalclass.innerclass;

class InputInnerClass {

    public InputInnerClass() {}

    class InnerWithPrivateCtor { // violation 'Class InnerWithPrivateCtor should be declared as final.'
        private InnerWithPrivateCtor() {}
    }

    private class PrivateInner { // violation 'Class PrivateInner should be declared as final.'
        private PrivateInner() {}
    }
}
