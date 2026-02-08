/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.design.FinalClassCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.finalclass.innerclass;

class OutputInnerClass {

    public OutputInnerClass() {}

    final class InnerWithPrivateCtor {
        private InnerWithPrivateCtor() {}
    }

    private final class PrivateInner {
        private PrivateInner() {}
    }
}
