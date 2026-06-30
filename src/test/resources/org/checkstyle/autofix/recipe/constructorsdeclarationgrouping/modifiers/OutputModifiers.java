/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.modifiers;

/**
 * Constructors with different access modifiers (public, protected, private) are
 * separated by a field. Verifies that modifiers are preserved correctly after moving.
 */
public class OutputModifiers {

    public OutputModifiers() {}

    protected OutputModifiers(String s) {}

    private OutputModifiers(int x) {}

    private int value;

}
