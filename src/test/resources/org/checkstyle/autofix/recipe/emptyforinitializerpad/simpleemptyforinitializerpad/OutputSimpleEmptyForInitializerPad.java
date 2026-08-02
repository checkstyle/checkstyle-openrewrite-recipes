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

public class OutputSimpleEmptyForInitializerPad {
    public void test() {
        for (; true; ) {
        }
    }
}
