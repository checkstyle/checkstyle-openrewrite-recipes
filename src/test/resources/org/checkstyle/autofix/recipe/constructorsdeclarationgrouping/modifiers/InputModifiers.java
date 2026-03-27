/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.modifiers;

/** Class with constructors of different access modifiers that are ungrouped. */
public class InputModifiers {

    public InputModifiers() {}

    protected InputModifiers(String s) {}

    private int value;

    private InputModifiers(int x) {} // violation 'Constructors should be grouped together'

}
