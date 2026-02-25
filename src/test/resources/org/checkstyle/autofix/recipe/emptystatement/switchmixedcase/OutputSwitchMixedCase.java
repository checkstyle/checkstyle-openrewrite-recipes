/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.EmptyStatementCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.emptystatement.switchmixedcase;

public class OutputSwitchMixedCase {

    public void test(int value) {
        switch (value) {

            case 1:
                break;

            case 2:
                int x = 5;
                break;

            case 3:
            default:
                System.out.println("done");
        }
    }
}
