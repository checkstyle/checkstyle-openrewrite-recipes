/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.innerenum;

/**
 * A private enum nested in a class has a constructor separated by a field.
 * Verifies that the fix works correctly inside enum bodies.
 */
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
