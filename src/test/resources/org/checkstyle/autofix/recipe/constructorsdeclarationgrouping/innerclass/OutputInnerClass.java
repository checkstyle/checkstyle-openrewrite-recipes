/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.innerclass;

/** Outer class with an inner class that has ungrouped constructors. */
public class OutputInnerClass {

    OutputInnerClass() {}

    private static class Inner {

        Inner() {}

        Inner(String s) {}

        Inner(int x) {}

        int x;

    }

}
