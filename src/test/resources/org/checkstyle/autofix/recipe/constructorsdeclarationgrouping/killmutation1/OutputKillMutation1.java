/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.annotation.MissingOverrideCheck">
      <message key="annotation.missing.override" value="Constructors should be grouped together"/>
    </module>
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck">
      <message key="constructors.declaration.grouping" value="Bait message"/>
    </module>
    <module name="com.puppycrawl.tools.checkstyle.filters.SuppressionSingleFilter">
      <property name="checks" value="ConstructorsDeclarationGrouping|MissingOverride"/>
      <property name="files" value="OutputKillMutation1\.java"/>
    </module>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.killmutation1;

public class OutputKillMutation1 {
    public OutputKillMutation1(int X) {}

    public void someOtherMethod() {}

    public OutputKillMutation1() {}

    public void methodThatSeparates() {}

    /** {@inheritDoc} */
    @Override
    public String toString() { return ""; }
}
