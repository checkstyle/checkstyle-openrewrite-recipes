/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.whitespace.EmptyForInitializerPadCheck">
      <property name="option" value="nospace"/>
    </module>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.emptyforinitializerpad.simpleemptyforinitializerpad;

public class InputSimpleEmptyForInitializerPad {
    public void test() {
        for ( ; true; ) { // violation "';' is preceded with whitespace."
        }
    }
}
