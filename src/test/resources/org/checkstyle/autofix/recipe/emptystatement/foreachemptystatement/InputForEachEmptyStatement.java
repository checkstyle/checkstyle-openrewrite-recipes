/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.EmptyStatementCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.emptystatement.foreachemptystatement;

import java.util.List;

public class InputForEachEmptyStatement {
    public void method(List<String> items) {
        for (String item : items)
            ; // violation 'Empty statement.'
    }

    public void methodWithBlock(List<String> items) {
        for (String item : items) {
            ; // violation 'Empty statement.'
        }
    }
}
