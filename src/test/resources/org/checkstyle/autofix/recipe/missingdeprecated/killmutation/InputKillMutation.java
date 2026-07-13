/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.design.FinalClassCheck"/>
    <module name="com.puppycrawl.tools.checkstyle.checks.annotation.MissingDeprecatedCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.missingdeprecated.killmutation;

public class InputKillMutation {

    /**
     * @deprecated
     */
    static class ClassB { // violation
    }

    static class ClassA { // violation
        private ClassA() {}
    }

}
