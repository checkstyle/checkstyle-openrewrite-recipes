/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.EmptyStatementCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.emptystatement.nesteddowhileloop;

public class OutputNestedDoWhileLoop {
    void method() {
        do {
        } while (true);
    }
}
