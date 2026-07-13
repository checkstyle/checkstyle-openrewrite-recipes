/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.annotation.MissingOverrideCheck"/>
    <module name="com.puppycrawl.tools.checkstyle.checks.annotation.MissingDeprecatedCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.missingoverride.killmutation;

public class InputKillMutation {
    /**
     * {@inheritDoc}
     */
    public String toString() { return ""; } // violation 'include @java.lang.Override annotation when '@inheritDoc''

    /**
     * @deprecated something
     */
    public void doSomethingElse() {} // violation 'Must include both @java.lang.Deprecated annotation and @deprecated Javadoc tag with description.'
}
