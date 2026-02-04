/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.EmptyStatementCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.emptystatement.emptystatementinblock;

public class InputEmptyStatementInBlock {
    void method() {
        int x = 1;
        ; // violation 'Empty statement.'
        int y = 2;
    }
}
