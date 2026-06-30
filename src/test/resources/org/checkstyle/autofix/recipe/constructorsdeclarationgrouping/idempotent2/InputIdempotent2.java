/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.idempotent2;

/**
 * Constructors that end up at violation locations after the first cycle of reordering
 * must not be wrongly moved again in a second cycle. Tests that already-moved
 * constructors stay put.
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
