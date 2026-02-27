/*xml
<module name="Checker">
  <module name="com.puppycrawl.tools.checkstyle.filters.SuppressionXpathSingleFilter">
    <property name="checks" value="UnusedLocalVariable"/>
    <property name="files" value="PathFilterClean"/>
  </module>
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.unusedlocalvariable.pathfilterclean;

public class InputPathFilterClean {
    public void method() {
        int used = 10;
        int unused = 20; // suppressed violation
        System.out.println(used);
    }
}
