/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.OneStatementPerLineCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.onestatementperline.statementsononeline;

public class InputStatementsOnOneLine {

    void declarations() {
        int first = 1; int second = 2; // violation 'Only one statement per line allowed.'
    }

    void loopHeader() {
        for (int index = 0; index < 2; index++) {
            System.out.println(index);
        }
    }
}
