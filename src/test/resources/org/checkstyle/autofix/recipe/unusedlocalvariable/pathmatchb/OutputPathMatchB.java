/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.pathmatchb;

public class OutputPathMatchB {

    public void method() {
        int used = 10;
        System.out.println(used);
    }
}
