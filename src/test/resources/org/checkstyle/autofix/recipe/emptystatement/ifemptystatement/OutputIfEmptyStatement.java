/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.EmptyStatementCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.emptystatement.ifemptystatement;

public class OutputIfEmptyStatement {

    public void test(boolean condition, boolean other) {
        if (condition) {
        }

        if (other) {
            int x = 1;
        }
    }
}
