/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.RequireThisCheck">
      <property name="validateOnlyOverlapping" value="false"/>
      <property name="checkMethods" value="false"/>
    </module>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.requirethis.allfields;

public class InputAllFields {
    int field1, field2, field3;
    String str = "hello";

    InputAllFields(int field1) {
        this.field1 = field1;
        field2 = 0; // violation 'Reference to instance variable 'field2' needs "this.".'
        foo(5);
    }

    void method2(int i) {
        foo(i);
        str.length(); // violation 'Reference to instance variable 'str' needs "this.".'
    }

    void foo(int field3) {
        field3 = field3; // violation 'Reference to instance variable 'field3' needs "this.".'
    }
}
