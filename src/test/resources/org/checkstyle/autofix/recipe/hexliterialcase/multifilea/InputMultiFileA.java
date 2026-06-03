/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.HexLiteralCaseCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.hexliterialcase.multifilea;

class InputMultiFileA {
    int h = 0xb; // violation 'Should use uppercase hexadecimal letters.'
}
