/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.modifiers;

/** Class with constructors of different access modifiers that are ungrouped. */
public class OutputModifiers {

    public OutputModifiers() {}

    protected OutputModifiers(String s) {}

    private OutputModifiers(int x) {}

    private int value;

}
