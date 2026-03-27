/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.separatedbyinnerclass;

/** Class where constructors are separated by an inner class declaration. */
public class InputSeparatedByInnerClass {

    InputSeparatedByInnerClass() {}

    InputSeparatedByInnerClass(String s) {}

    private static class Helper {}

    InputSeparatedByInnerClass(int x) {} // violation 'Constructors should be grouped together'

}
