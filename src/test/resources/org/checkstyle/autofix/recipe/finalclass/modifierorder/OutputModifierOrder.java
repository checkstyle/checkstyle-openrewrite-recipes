/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.design.FinalClassCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.finalclass.modifierorder;

public final class OutputModifierOrder {
    private OutputModifierOrder() {
    }

    public static final class StaticInnerClass {
        private StaticInnerClass() {
        }
    }
}
