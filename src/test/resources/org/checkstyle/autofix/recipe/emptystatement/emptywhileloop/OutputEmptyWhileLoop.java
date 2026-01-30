/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.EmptyStatementCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.emptystatement.emptywhileloop;

public class OutputEmptyWhileLoop {
    void method() {
        while (true) {}
    }

    void conditionLoop(boolean condition) {
        while (condition) {}
    }
}
