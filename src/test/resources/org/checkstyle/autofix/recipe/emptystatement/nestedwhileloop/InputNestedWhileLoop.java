/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.EmptyStatementCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.emptystatement.nestedwhileloop;

public class InputNestedWhileLoop {
    void method() {
        while (true) {
            ; // violation 'Empty statement.'
        }
    }
}
