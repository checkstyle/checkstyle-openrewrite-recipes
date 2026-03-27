/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module
        name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.samelinedeclarations;

public class InputSameLineDeclarations {

    int x;

    InputSameLineDeclarations() {} InputSameLineDeclarations(String s) {}

    void foo() {}    InputSameLineDeclarations(int x) {}
    // violation above 'Constructors should be grouped together.*'

    InputSameLineDeclarations(String s, int x) {}
    // violation above 'Constructors should be grouped together.*'
}
