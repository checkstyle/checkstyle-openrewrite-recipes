/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.EmptyStatementCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.emptystatement.emptyifstatement;

public class OutputEmptyIfStatement {
    void method(boolean condition) {
        if (condition) {}
    }

    void methodWithElse(boolean condition) {
        if (condition) {}
        else {
            System.out.println("else");
        }
    }
}
