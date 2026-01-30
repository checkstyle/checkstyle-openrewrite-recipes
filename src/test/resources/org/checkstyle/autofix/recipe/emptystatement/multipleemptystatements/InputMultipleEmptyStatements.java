/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.EmptyStatementCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.emptystatement.multipleemptystatements;

public class InputMultipleEmptyStatements {
    void method() {
        int x = 5;; // violation 'Empty statement.'
        ; // violation 'Empty statement.'
    }
}
