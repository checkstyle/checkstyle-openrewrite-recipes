/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.NumericalPrefixesInfixesSuffixesCharacterCaseCheck"/>
    <module name="com.puppycrawl.tools.checkstyle.checks.UpperEllCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.numericalprefixesinfixessuffixescharactercase.killmutation;

public class OutputKillMutation {
    long trigger = 0x1L;
    long value = 123L;
}
