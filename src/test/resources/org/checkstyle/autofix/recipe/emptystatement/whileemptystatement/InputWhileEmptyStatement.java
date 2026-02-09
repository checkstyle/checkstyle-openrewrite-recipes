/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.EmptyStatementCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.emptystatement.whileemptystatement;

public class InputWhileEmptyStatement {

    public void test(boolean condition) {
        while (condition)
            ; // violation 'Empty statement.'

        do
            ; // violation 'Empty statement.'
        while (condition);

        while (!condition) {
            System.out.println("hello");
        }
    }
}
