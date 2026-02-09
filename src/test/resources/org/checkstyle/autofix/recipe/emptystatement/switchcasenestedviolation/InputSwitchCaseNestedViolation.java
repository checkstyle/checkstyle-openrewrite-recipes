/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.EmptyStatementCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.emptystatement.switchcasenestedviolation;

public class InputSwitchCaseNestedViolation {

    public void test(int x) {
        switch (x) {
            case 1:
                System.out.println("one");
                if (true) {
                    ; // violation 'Empty statement.'
                }
                break;
            case 2:
                System.out.println("two");
                break;
            default:
                break;
        }
    }
}
