/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module
        name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.classendswithconstructor;

/**
 * A class whose last declaration is a constructor, and an inner class whose last
 * declaration is also a constructor. Verifies that no IndexOutOfBoundsException
 * occurs when the constructor group extends to the end of the class body.
 */
public class InputClassEndsWithConstructor {

    InputClassEndsWithConstructor(int x) {}

    void foo() {}

    InputClassEndsWithConstructor() {} // violation 'Constructors should be grouped together.*'

    static class AnotherClass {

        AnotherClass() {}

        AnotherClass(int x) {} // ends with constructor
    }
}
