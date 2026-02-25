/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.EmptyStatementCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.emptystatement.foreachmixedcase;

import java.util.List;

public class OutputForEachMixedCase {

    public void test(List<String> items) {

        for (String item : items) {
        }

        for (String item : items) {
            System.out.println(item);
        }

        for (String item : items) {
        }
    }
}
