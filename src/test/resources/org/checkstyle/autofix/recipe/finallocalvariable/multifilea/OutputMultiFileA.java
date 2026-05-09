/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.FinalLocalVariableCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.finallocalvariable.multifilea;

public class OutputMultiFileA {
    public void test() {
        final int a = 1;
    }
}
