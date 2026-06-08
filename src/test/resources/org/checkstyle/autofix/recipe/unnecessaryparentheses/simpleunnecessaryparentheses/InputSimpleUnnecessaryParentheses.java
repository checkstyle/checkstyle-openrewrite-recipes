/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnnecessaryParenthesesCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.unnecessaryparentheses.simpleunnecessaryparentheses;

public class InputSimpleUnnecessaryParentheses {
    public int square(int a, int b, int c) {
        int square = (a * b); // violation 'Unnecessary parentheses around assignment right-hand side'
        int y = (a + b) * c;
        int z = (a + b) * (c); // violation 'Unnecessary parentheses around identifier'
        int w = (a) + (b * c); // violation 'Unnecessary parentheses around identifier'
        return (square); // violation 'Unnecessary parentheses around identifier'
    }
}
