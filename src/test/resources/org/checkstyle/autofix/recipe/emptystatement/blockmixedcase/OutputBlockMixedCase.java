/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.EmptyStatementCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.emptystatement.blockmixedcase;

public class OutputBlockMixedCase {

    public void test() {

        int x = 10;

        {
            int y = 20;
        }
    }
}
