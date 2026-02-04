/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.EmptyStatementCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.emptystatement.nesteddowhileloop;

public class InputNestedDoWhileLoop {
    void method() {
        do {
            ; // violation 'Empty statement.'
        } while (true);
    }
}
