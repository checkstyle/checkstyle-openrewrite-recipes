/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.EmptyStatementCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.emptystatement.emptydowhileloop;

public class OutputEmptyDoWhileLoop {
    void method() {
        do {} while (true);
    }

    void conditionLoop(boolean condition) {
        do {} while (condition);
    }
}
