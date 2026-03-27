/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.innerenum;

/** Class containing an enum with ungrouped constructors. */
public class InputInnerEnum {

    InputInnerEnum() {}

    private enum Status {

        ACTIVE, INACTIVE;

        Status() {}

        Status(String label) {}

        final int code = 0;

        Status(int code) {} // violation 'Constructors should be grouped together'

        void display() {}

    }

}
