/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.EmptyStatementCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.emptystatement.emptyforloop;

public class InputEmptyForLoop {
    void method() {
        for (int i = 0; i < 10; i++); // violation 'Empty statement.'
    }

    void infiniteLoop() {
        for (;;); // violation 'Empty statement.'
    }
}
