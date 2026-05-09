/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.FinalLocalVariableCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.finallocalvariable.multifileb;

public class InputMultiFileB {
    public void test() {
        int a = 1;
        a = 2;
    }
}
