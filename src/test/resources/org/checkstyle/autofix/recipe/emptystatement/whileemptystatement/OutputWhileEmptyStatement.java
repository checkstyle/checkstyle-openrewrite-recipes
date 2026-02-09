/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.EmptyStatementCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.emptystatement.whileemptystatement;

public class OutputWhileEmptyStatement {

    public void test(boolean condition) {
        while (condition) {
        }

        do {
        }
        while (condition);

        while (!condition) {
            System.out.println("hello");
        }
    }
}
