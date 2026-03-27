/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module
        name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.noconstructors;

/**
 * Class without any constructors.
 */
public class InputNoConstructors {

    private void doNothing() {}

    public static void main(String... args) {}
}
