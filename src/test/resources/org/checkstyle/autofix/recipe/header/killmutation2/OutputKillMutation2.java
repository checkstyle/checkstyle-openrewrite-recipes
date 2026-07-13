/*xml
<module name="Checker">
  <module name="com.puppycrawl.tools.checkstyle.checks.header.HeaderCheck">
    <property name="headerFile" value="src/test/resources/org/checkstyle/autofix/recipe/header/killmutation.txt"/>
    <property name="ignoreLines" value="1,2,3,4,5,6,7,8,9,10,11"/>
  </module>
  <module name="com.puppycrawl.tools.checkstyle.checks.NewlineAtEndOfFileCheck">
    <property name="lineSeparator" value="lf"/>
  </module>
</module>
*/
// MATCH THIS
package org.checkstyle.autofix.recipe.header.killmutation2;

public class OutputKillMutation2 {
}
