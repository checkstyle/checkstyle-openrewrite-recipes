/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.UpperEllCheck"/>
    <module name="com.puppycrawl.tools.checkstyle.checks.HexLiteralCaseCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.hexliterialcase.killmutation;

public class InputKillMutation {
    long value1 = 0x1aL; // violation 'Should use uppercase hexadecimal letters.'
    long value2 = 0l; // violation 'Should use uppercase 'L'.'
}
