/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.design.FinalClassCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.finalclass.staticinnerclass;

class OutputStaticInnerClass {

    OutputStaticInnerClass() {}

    static final class StaticInner {
        private StaticInner() {}
    }
}
