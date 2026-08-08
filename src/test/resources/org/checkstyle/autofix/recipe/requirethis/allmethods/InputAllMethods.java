/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.RequireThisCheck">
      <property name="validateOnlyOverlapping" value="false"/>
      <property name="checkFields" value="false"/>
    </module>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.requirethis.allmethods;

public class InputAllMethods {
    int field1, field2, field3;

    InputAllMethods(int field1) {
        this.field1 = field1;
        field2 = 0;
        foo(5); // violation 'Method call to 'foo' needs "this.".'
    }

    void method2(int i) {
        foo(i); // violation 'Method call to 'foo' needs "this.".'
    }

    void foo(int field3) {
        field3 = field3;
    }
}
