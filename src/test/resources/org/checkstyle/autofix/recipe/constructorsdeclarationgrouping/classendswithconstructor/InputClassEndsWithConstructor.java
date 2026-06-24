/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module
        name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.classendswithconstructor;

public class InputClassEndsWithConstructor {

    InputClassEndsWithConstructor(int x) {}

    void foo() {}

    InputClassEndsWithConstructor() {} // violation 'Constructors should be grouped together.*'

    static class AnotherClass {

        AnotherClass() {}

        AnotherClass(int x) {} // ends with constructor
    }
}
