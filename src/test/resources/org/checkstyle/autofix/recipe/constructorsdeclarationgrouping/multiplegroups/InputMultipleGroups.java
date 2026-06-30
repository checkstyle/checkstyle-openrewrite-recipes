/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.multiplegroups;

/**
 * Both the outer class and a nested static inner class independently have
 * ungrouped constructors. Verifies that the fix is applied correctly and
 * independently in each class scope.
 */
public class InputMultipleGroups {

    InputMultipleGroups() {}

    void outerMethod() {}

    InputMultipleGroups(int x) {} // violation 'Constructors should be grouped together'

    private static class Helper {

        Helper() {}

        String name;

        Helper(String name) {} // violation 'Constructors should be grouped together'

        void helperMethod() {}

    }

}
