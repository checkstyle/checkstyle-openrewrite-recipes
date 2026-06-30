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
public class OutputMultipleGroups {

    OutputMultipleGroups() {}

    OutputMultipleGroups(int x) {}

    void outerMethod() {}

    private static class Helper {

        Helper() {}

        Helper(String name) {}

        String name;

        void helperMethod() {}

    }

}
