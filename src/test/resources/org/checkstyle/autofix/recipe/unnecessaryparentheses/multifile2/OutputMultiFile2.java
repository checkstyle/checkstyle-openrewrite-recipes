/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnnecessaryParenthesesCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.unnecessaryparentheses.multifile;

public class OutputMultiFile2 {
    public int square2(int a, int b, int c) {
        return (a + b) * c;
    }
}
