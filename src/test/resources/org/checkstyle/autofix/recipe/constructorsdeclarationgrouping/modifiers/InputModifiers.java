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
public class InputModifiers {

    public InputModifiers() {}

    protected InputModifiers(String s) {}

    private int value;

    private InputModifiers(int x) {} // violation 'Constructors should be grouped together'

}
