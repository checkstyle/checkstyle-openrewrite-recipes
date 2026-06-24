/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module
        name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.classendswithconstructor;

public class OutputClassEndsWithConstructor {

    OutputClassEndsWithConstructor(int x) {}

    OutputClassEndsWithConstructor() {}

    void foo() {}

    static class AnotherClass {

        AnotherClass() {}

        AnotherClass(int x) {} // ends with constructor
    }
}
