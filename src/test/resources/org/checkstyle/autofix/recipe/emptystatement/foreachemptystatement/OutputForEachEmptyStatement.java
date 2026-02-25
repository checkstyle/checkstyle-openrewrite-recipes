/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.EmptyStatementCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.emptystatement.foreachemptystatement;

import java.util.List;

public class OutputForEachEmptyStatement {
    public void method(List<String> items) {
        for (String item : items) {
        }
    }

    public void methodWithBlock(List<String> items) {
        for (String item : items) {
        }
    }
}
