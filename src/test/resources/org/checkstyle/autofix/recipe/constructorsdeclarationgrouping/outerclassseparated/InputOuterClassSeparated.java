/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.outerclassseparated;

/** Class with fields and methods before any constructor, then a separated constructor at the end. */
public class InputOuterClassSeparated {

    int x;
    int y;

    void init() {}

    InputOuterClassSeparated(int x) {}

    void process() {}

    InputOuterClassSeparated(int x, int y) {} // violation 'Constructors should be grouped together'

}
