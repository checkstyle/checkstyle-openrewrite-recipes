/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.innerclass;

/** Outer class with an inner class that has ungrouped constructors. */
public class InputInnerClass {

    InputInnerClass() {}

    private static class Inner {

        Inner() {}

        Inner(String s) {}

        int x;

        Inner(int x) {} // violation 'Constructors should be grouped together'

    }

}
