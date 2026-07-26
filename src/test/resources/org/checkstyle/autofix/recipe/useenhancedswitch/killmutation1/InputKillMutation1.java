/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.MissingSwitchDefaultCheck"/>
    <module name="SuppressionXpathSingleFilter">
      <property name="checks" value="UseEnhancedSwitchCheck"/>
      <property name="query" value="//METHOD_DEF[./IDENT[@text='bar']]/SLIST/LITERAL_SWITCH"/>
    </module>
    <module name="SuppressionXpathSingleFilter">
      <property name="checks" value="MissingSwitchDefaultCheck"/>
      <property name="query" value="//METHOD_DEF[./IDENT[@text='foo']]/SLIST/LITERAL_SWITCH"/>
    </module>
    <module name="com.puppycrawl.tools.checkstyle.filters.SuppressionSingleFilter">
      <property name="checks" value="MissingSwitchDefaultCheck"/>
      <property name="files" value="OutputKillMutation1.java"/>
    </module>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.useenhancedswitch.killmutation1;

public class InputKillMutation1 {
    void foo(int x) {
        switch (x) { // violation
            case 1:
                break;
        }
    }

    void bar(int y) {
        switch (y) { // violation
            case 1:
                break;
        }
    }
}
