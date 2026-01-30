/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.EmptyStatementCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.emptystatement.nestedemptystatements;

public class OutputNestedEmptyStatements {
    void method(boolean condition) {
        if (condition) {
            int x = 5;
        }
    }
}
