/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.stalealignment;

public class OutputStaleAlignment {
  public void method1() {
  }

  public void method2() {
    int dummy = 1;
    System.out.println(dummy);
  }
}
