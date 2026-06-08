/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnnecessaryParenthesesCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.unnecessaryparentheses.simpleunnecessaryparentheses;

public class OutputSimpleUnnecessaryParentheses {
    public int square(int a, int b, int c) {
        int square = a * b;
        int y = (a + b) * c;
        int z = (a + b) * c;
        int w = a + (b * c);
        return square;
    }
}
