/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.EmptyStatementCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.emptystatement.nestedemptystatements;

public class InputNestedEmptyStatements {
    void method(boolean condition) {
        if (condition) {
            ; // violation 'Empty statement.'
            int x = 5;; // violation 'Empty statement.'
            ; // violation 'Empty statement.'
        }
    }
}
