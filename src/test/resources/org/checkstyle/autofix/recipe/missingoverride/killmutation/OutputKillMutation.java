/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.annotation.MissingOverrideCheck"/>
    <module name="com.puppycrawl.tools.checkstyle.checks.annotation.MissingDeprecatedCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.missingoverride.killmutation;

public class OutputKillMutation {
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() { return ""; }

    /**
     * @deprecated something
     */
    @Deprecated
    public void doSomethingElse() {}
}
