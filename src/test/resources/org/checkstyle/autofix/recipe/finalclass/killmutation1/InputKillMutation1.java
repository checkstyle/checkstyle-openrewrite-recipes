/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.design.FinalClassCheck"/>
    <module name="com.puppycrawl.tools.checkstyle.checks.annotation.MissingDeprecatedCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.finalclass.killmutation1;

public class InputKillMutation1 {

    static class ClassA { // violation
        private ClassA() {}
    }

    /**
     * @deprecated
     */
    static class ClassB { // violation
    }
}
