/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.outerclassseparated;

/**
 * The first constructor is preceded by fields and methods; a second constructor
 * appears after another method. Verifies that the fix works when the constructor
 * group does not start at the top of the class body.
 */
public class InputOuterClassSeparated {

    int x;
    int y;

    void init() {}

    InputOuterClassSeparated(int x) {}

    void process() {}

    InputOuterClassSeparated(int x, int y) {} // violation 'Constructors should be grouped together'

}
