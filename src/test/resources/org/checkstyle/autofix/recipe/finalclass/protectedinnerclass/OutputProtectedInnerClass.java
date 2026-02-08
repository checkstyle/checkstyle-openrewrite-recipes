/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.design.FinalClassCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.finalclass.protectedinnerclass;

class OutputProtectedInnerClass {

    OutputProtectedInnerClass() {}

    protected final class Inner {
        private Inner() {
        }
    }
}
