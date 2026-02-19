/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.annotation.MissingOverrideCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.missingoverride.overridecase;

public class InputOverrideCase implements TestInterface {
    /**
     * {@inheritDoc}
     */
    public void doSomething() {} // violation 'include @java.lang.Override annotation when '@inheritDoc''

    /**
     * {@inheritDoc}
     */
    public void doFoo2() {} // violation 'include @java.lang.Override annotation when '@inheritDoc''
}
