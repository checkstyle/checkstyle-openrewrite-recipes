/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.separatedbyinnerclass;

/** Class where constructors are separated by an inner class declaration. */
public class OutputSeparatedByInnerClass {

    OutputSeparatedByInnerClass() {}

    OutputSeparatedByInnerClass(String s) {}

    OutputSeparatedByInnerClass(int x) {}

    private static class Helper {}

}
