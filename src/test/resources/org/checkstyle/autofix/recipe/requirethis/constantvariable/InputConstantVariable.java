/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.RequireThisCheck">
      <property name="validateOnlyOverlapping" value="false"/>
    </module>
    <module name="com.puppycrawl.tools.checkstyle.filters.SuppressionSingleFilter">
      <property name="checks" value="RequireThis"/>
      <property name="files" value="OutputConstantVariable.java"/>
    </module>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.requirethis.constantvariable;

public class InputConstantVariable {
    private final short epoch = 0;
    private final String name = "test";
    
    public void method() {
        final short bumpedEpoch = epoch + 1; // violation 'Reference to instance variable 'epoch' needs "this.".'
        final String bumpedName = name + "1"; // violation 'Reference to instance variable 'name' needs "this.".'
    }
}
