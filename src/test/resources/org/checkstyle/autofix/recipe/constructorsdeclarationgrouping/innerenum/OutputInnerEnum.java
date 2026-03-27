/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.innerenum;

/** Class containing an enum with ungrouped constructors. */
public class OutputInnerEnum {

    OutputInnerEnum() {}

    private enum Status {

        ACTIVE, INACTIVE;

        Status() {}

        Status(String label) {}

        Status(int code) {}

        final int code = 0;

        void display() {}

    }

}
