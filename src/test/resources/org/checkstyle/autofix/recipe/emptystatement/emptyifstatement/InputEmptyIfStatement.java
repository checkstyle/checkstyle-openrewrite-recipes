/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.EmptyStatementCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.emptystatement.emptyifstatement;

public class InputEmptyIfStatement {
    void method(boolean condition) {
        if (condition); // violation 'Empty statement.'
    }

    void methodWithElse(boolean condition) {
        if (condition); // violation 'Empty statement.'
        else {
            System.out.println("else");
        }
    }
}
