/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.stalealignment;

public class InputStaleAlignment {
  public void method1() {
    int dummy = 1; // violation 'Unused named local variable'
  }

  public void method2() {
    int dummy = 1;
    System.out.println(dummy);
  }
}
