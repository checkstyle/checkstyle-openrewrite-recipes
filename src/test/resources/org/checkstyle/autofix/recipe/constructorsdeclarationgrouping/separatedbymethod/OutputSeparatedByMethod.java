/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.separatedbymethod;

/** Class where constructors are separated by a method. */
public class OutputSeparatedByMethod {

    OutputSeparatedByMethod() {}

    OutputSeparatedByMethod(String s) {}

    OutputSeparatedByMethod(int x) {}

    void helper() {}

}
