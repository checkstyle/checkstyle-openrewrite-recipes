/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.EmptyStatementCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.emptystatement.emptywhileloop;

public class InputEmptyWhileLoop {
    void method() {
        while (true); // violation 'Empty statement.'
    }

    void conditionLoop(boolean condition) {
        while (condition); // violation 'Empty statement.'
    }
}
