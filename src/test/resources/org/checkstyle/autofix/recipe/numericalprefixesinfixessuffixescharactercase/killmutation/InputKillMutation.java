/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.NumericalPrefixesInfixesSuffixesCharacterCaseCheck"/>
    <module name="com.puppycrawl.tools.checkstyle.checks.UpperEllCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.numericalprefixesinfixessuffixescharactercase.killmutation;

public class InputKillMutation {
    long trigger = 0X1L; // violation 'Numerical prefix should be in lowercase.'
    long value = 123l; // violation 'Should use uppercase 'L'.'
}
