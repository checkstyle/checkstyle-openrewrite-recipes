/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.design.FinalClassCheck"/>
    <module name="com.puppycrawl.tools.checkstyle.checks.annotation.MissingDeprecatedCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.missingdeprecated.killmutation;

public class OutputKillMutation {

    /**
     * @deprecated
     */
    @Deprecated
    static class ClassB {
    }

    static final class ClassA {
        private ClassA() {}
    }

}
