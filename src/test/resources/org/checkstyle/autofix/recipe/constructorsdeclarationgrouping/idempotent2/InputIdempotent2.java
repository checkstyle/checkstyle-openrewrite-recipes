/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.idempotent2;

/**
 * The constructor Idempotent2(String b) ends up at the violation location after first cycle.
 * It should remain at the same position after second cycle.
 */
public class InputIdempotent2 {

    InputIdempotent2() {}

    int a;

    InputIdempotent2(int a) {} // violation 'Constructors should be grouped together'

    String b;

    InputIdempotent2(String b) {} // violation 'Constructors should be grouped together'

    void foo() {}

    InputIdempotent2(int a, String b) {} // violation 'Constructors should be grouped together'

}
