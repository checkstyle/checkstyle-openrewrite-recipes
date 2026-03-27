/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.multiplegroups;

/** Class where both the outer class and an inner class have ungrouped constructors. */
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
