/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.error2;

/**
 * The constructor InputError2(String b) ends up at the violation location after first cycle.
 * It should remain at the same position after second cycle.
 */
public class InputError2 {

    InputError2() {}

    int a;

    InputError2(int a) {} // violation 'Constructors should be grouped together'

    String b;

    InputError2(String b) {} // violation 'Constructors should be grouped together'

    void foo() {}

    InputError2(int a, String b) {} // violation 'Constructors should be grouped together'

}
