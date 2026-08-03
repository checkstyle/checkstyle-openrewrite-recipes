/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.whitespace.EmptyForInitializerPadCheck">
      <property name="option" value="space"/>
    </module>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.emptyforinitializerpad.spaceoptionemptyforinitializerpad;

public class InputSpaceOptionEmptyForInitializerPad {
    public void test() {
        for (; ; ) { // violation "';' is not preceded with whitespace."
            break;
        }
    }
}
