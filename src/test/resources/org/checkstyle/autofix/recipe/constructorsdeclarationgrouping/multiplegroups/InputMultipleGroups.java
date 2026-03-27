/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.multiplegroups;

/** Class where both the outer class and an inner class have ungrouped constructors. */
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
