/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.separatedbyinnerclass;

/**
 * A single constructor is separated from the initial group by an inner class declaration.
 * Inner class declarations are non-constructor members and therefore trigger a violation.
 * Verifies the inner-class-separation scenario.
 */
public class OutputSeparatedByInnerClass {

    OutputSeparatedByInnerClass() {}

    OutputSeparatedByInnerClass(String s) {}

    OutputSeparatedByInnerClass(int x) {}

    private static class Helper {}

}
