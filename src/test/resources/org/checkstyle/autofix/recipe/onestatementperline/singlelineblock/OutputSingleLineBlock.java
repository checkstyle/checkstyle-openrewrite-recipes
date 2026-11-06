/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.OneStatementPerLineCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.onestatementperline.singlelineblock;

public class OutputSingleLineBlock {

    void test() {
        int first = 1;
        int second = 2;
    }
}
