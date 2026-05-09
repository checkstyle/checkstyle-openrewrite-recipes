/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.FinalLocalVariableCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.finallocalvariable.multifilea;

public class InputMultiFileA {
    public void test() {
        int a = 1; // violation, "should be declared final"
    }
}
