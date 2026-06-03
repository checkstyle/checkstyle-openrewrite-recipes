/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.HexLiteralCaseCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.hexliterialcase.multifilea;

class OutputMultiFileA {
    int h = 0xB;
}
