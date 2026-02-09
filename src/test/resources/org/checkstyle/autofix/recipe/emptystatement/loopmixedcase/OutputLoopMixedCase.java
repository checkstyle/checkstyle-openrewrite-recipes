/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.EmptyStatementCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.emptystatement.loopmixedcase;

public class OutputLoopMixedCase {

    public void test(boolean cond) {

        while (cond) {
        }

        while (cond) {
            System.out.println("keep");
        }

        while (cond) {
        }
    }
}
