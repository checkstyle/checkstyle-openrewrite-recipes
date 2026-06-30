/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.innerclass;

/**
 * An inner static class has a constructor separated from its group by a field.
 * Verifies that the fix is correctly scoped to the inner class body without
 * affecting the outer class.
 */
public class InputInnerClass {

    InputInnerClass() {}

    private static class Inner {

        Inner() {}

        Inner(String s) {}

        int x;

        Inner(int x) {} // violation 'Constructors should be grouped together'

    }

}
