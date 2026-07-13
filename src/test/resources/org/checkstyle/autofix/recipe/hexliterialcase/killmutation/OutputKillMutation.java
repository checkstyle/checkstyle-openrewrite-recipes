/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.UpperEllCheck"/>
    <module name="com.puppycrawl.tools.checkstyle.checks.HexLiteralCaseCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.hexliterialcase.killmutation;

public class OutputKillMutation {
    long value1 = 0x1AL;
    long value2 = 0L;
}
