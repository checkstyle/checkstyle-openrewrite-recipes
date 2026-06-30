/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.separatedbymethod;

/**
 * A single constructor is separated from the initial group by a method.
 * Verifies the minimal method-separation scenario.
 */
public class InputSeparatedByMethod {

    InputSeparatedByMethod() {}

    InputSeparatedByMethod(String s) {}

    void helper() {}

    InputSeparatedByMethod(int x) {} // violation 'Constructors should be grouped together'

}
