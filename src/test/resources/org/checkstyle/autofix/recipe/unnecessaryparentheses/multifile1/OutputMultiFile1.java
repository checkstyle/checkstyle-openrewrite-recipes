/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnnecessaryParenthesesCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.unnecessaryparentheses.multifile;

public class OutputMultiFile1 {
    public int square1(int a, int b) {
        return a * b;
    }
}
