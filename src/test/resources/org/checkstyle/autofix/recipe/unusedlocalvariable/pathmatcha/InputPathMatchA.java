/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.pathmatcha;

public class InputPathMatchA {

    public void method() {
        int used = 10;


        int unused = 20; // violation 'Unused named local variable'
        System.out.println(used);
    }
}
