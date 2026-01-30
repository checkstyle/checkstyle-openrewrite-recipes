/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.EmptyStatementCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.emptystatement.emptydowhileloop;

public class InputEmptyDoWhileLoop {
    void method() {
        do; while (true); // violation 'Empty statement.'
    }

    void conditionLoop(boolean condition) {
        do; while (condition); // violation 'Empty statement.'
    }
}
