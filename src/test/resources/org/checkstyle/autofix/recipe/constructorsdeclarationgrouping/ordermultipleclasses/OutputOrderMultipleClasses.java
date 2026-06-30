/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module
        name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck">
      <property name="orderByIncreasingParameterCount" value="true"/>
    </module>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.ordermultipleclasses;

/**
 * An outer class and a static inner class each independently have
 * out-of-order constructors. Verifies that the ordering fix is
 * correctly scoped to each class body.
 */
public class OutputOrderMultipleClasses {

    OutputOrderMultipleClasses() {}

    OutputOrderMultipleClasses(String s) {}

    OutputOrderMultipleClasses(int x) {}

    static class Inner {

        Inner() {}

        Inner(String s) {}

        Inner(String s, int x) {}
    }
}
