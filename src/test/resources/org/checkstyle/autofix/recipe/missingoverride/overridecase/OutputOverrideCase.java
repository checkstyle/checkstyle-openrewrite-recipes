/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.annotation.MissingOverrideCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.missingoverride.overridecase;

public class OutputOverrideCase implements TestInterface {
    /**
     * {@inheritDoc}
     */
    @Override
    public void doSomething() {}

    /**
     * {@inheritDoc}
     */
    @Override
    public void doFoo2() {}
}
