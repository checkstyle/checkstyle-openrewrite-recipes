/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.UpperEllCheck"/>
    <module name="com.puppycrawl.tools.checkstyle.checks.HexLiteralCaseCheck"/>
  </module>
  <module name="com.puppycrawl.tools.checkstyle.filters.SuppressionSingleFilter">
    <property name="checks" value="UpperEll"/>
    <property name="lines" value="20"/>
  </module>
  <module name="com.puppycrawl.tools.checkstyle.filefilters.BeforeExecutionExclusionFileFilter">
    <property name="fileNamePattern" value="Output.*\.java$"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.upperell.killmutation;

public class InputKillMutation {
    long a = 1l; // violation
    long b = 0x2al; // violation
}
