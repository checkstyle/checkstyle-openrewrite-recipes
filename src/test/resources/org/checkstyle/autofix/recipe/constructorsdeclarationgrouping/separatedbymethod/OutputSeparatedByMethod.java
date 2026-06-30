/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.separatedbymethod;

/**
 * A single constructor is separated from the initial group by a method.
 * Verifies the minimal method-separation scenario.
 */
public class OutputSeparatedByMethod {

    OutputSeparatedByMethod() {}

    OutputSeparatedByMethod(String s) {}

    OutputSeparatedByMethod(int x) {}

    void helper() {}

}
