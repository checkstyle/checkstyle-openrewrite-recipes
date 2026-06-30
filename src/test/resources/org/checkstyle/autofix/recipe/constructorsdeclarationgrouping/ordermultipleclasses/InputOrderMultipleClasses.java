/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module
        name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck">
      <property name="orderByIncreasingParameterCount" value="true"/>
    </module>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.ordermultipleclasses;

public class InputOrderMultipleClasses {

    InputOrderMultipleClasses(String s) {}

    InputOrderMultipleClasses() {}
    // violation above 'Constructors should be ordered.*'

    InputOrderMultipleClasses(int x) {}
    // violation above 'Constructors should be ordered.*'

    static class Inner {

        Inner(String s, int x) {}

        Inner(String s) {}
        // violation above 'Constructors should be ordered.*'

        Inner() {}
        // violation above 'Constructors should be ordered.*'
    }
}
