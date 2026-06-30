/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module
        name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.samelinedeclarations;

/**
 * Multiple constructors or methods are declared on the same line.
 * Verifies that ungrouped constructors are moved correctly.
 */
public class OutputSameLineDeclarations {

    int x;

    OutputSameLineDeclarations() {} OutputSameLineDeclarations(String s) {}    OutputSameLineDeclarations(int x) {}

    OutputSameLineDeclarations(String s, int x) {}

    void foo() {}
}
