/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module
        name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.samelinedeclarations;

public class OutputSameLineDeclarations {

    int x;

    OutputSameLineDeclarations() {} OutputSameLineDeclarations(String s) {}    OutputSameLineDeclarations(int x) {}

    OutputSameLineDeclarations(String s, int x) {}

    void foo() {}
}
