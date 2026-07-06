/*xml
<module name="Checker">
  <module name="com.puppycrawl.tools.checkstyle.checks.header.HeaderCheck">
    <property name="header" value="// This is a header"/>
  </module>
  <module name="com.puppycrawl.tools.checkstyle.checks.NewlineAtEndOfFileCheck">
    <property name="lineSeparator" value="lf"/>
  </module>
</module>
*/
// violation 10 lines above
// violation 11 lines above
package org.checkstyle.autofix.recipe.newlineatendoffile.killmutation1;

class InputKillMutation1 {
    public void test() {}
}