/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.EmptyStatementCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.emptystatement.emptystatementinblock;

public class OutputEmptyStatementInBlock {
    void method() {
        int x = 1;
        int y = 2;
    }
}
