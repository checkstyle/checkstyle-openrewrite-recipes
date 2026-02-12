/*xml
<module name="Checker">
  <module name="com.puppycrawl.tools.checkstyle.filters.SuppressionXpathSingleFilter">
    <property name="checks" value="HexLiteralCase"/>
    <property name="query" value="//NUM_INT[@text='0xabc']"/>
    <property name="query" value="//NUM_FLOAT[@text='0xab.1p1f']"/>
  </module>
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.HexLiteralCaseCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.hexliterialcase.hexliteralcasethree;

public class InputHexLiteralCaseThree {
    int x = 0xabc + 0xdef; // violation 'Should use uppercase hexadecimal letters.'
    int y = 0xab; // violation 'Should use uppercase hexadecimal letters.'

    float f = 0xab.1p1f + 0xb.1p1f; // violation 'Should use uppercase hexadecimal letters.'
}
