/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.EmptyStatementCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.emptystatement.blockmixedcase;

public class InputBlockMixedCase {

    public void test() {

        ; // violation 'Empty statement.'

        int x = 10;

        ; // violation 'Empty statement.'

        {
            int y = 20;
        }
    }
}
