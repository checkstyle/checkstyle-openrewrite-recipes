/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.design.FinalClassCheck"/>
    <module name="com.puppycrawl.tools.checkstyle.checks.annotation.MissingDeprecatedCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.finalclass.killmutation1;

public class OutputKillMutation1 {

    static final class ClassA {
        private ClassA() {}
    }

    /**
     * @deprecated
     */
    @Deprecated
    static class ClassB {
    }
}
