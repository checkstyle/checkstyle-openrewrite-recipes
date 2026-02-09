/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.EmptyStatementCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.emptystatement.loopmixedcase;

public class InputLoopMixedCase {

    public void test(boolean cond) {

        while (cond)
            ; // violation 'Empty statement.'

        while (cond) {
            System.out.println("keep");
        }

        while (cond) {
            ; // violation 'Empty statement.'
        }
    }
}
