/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.EmptyStatementCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.emptystatement.foreachmixedcase;

import java.util.List;

public class InputForEachMixedCase {

    public void test(List<String> items) {

        for (String item : items)
            ; // violation 'Empty statement.'

        for (String item : items) {
            System.out.println(item);
        }

        for (String item : items) {
            ; // violation 'Empty statement.'
        }
    }
}
