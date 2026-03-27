/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module
        name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.simple;

public class OutputSimple {

    int x;

    OutputSimple() {}

    OutputSimple(String s) {}

    OutputSimple(int x) {}

    void foo() {}
}
