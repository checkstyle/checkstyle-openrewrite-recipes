/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module
        name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.noconstructors;

/** There are no constructors at all. Verifies that no modification is made. */
public class OutputNoConstructors {

    private void doNothing() {}

    public static void main(String... args) {}
}
