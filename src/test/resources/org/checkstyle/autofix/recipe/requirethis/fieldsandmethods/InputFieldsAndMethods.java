/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.RequireThisCheck">
      <property name="validateOnlyOverlapping" value="false"/>
    </module>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.requirethis.fieldsandmethods;

public class InputFieldsAndMethods {
    int field1, field2, field3;

    InputFieldsAndMethods(int field1) {
        this.field1 = field1;
        field2 = 0; // violation 'Reference to instance variable 'field2' needs "this.".'
        foo(5); // violation 'Method call to 'foo' needs "this.".'
    }

    void method2(int i) {
        foo(i); // violation 'Method call to 'foo' needs "this.".'
        foo( // violation 'Method call to 'foo' needs "this.".'
            field1); // violation 'Reference to instance variable 'field1' needs "this.".'
        staticMethod();
        int j = staticField;
    }

    void foo(int field3) {
        field3 = field3; // violation 'Reference to instance variable 'field3' needs "this.".'
    }

    int getField1() {
        return field1; // violation 'Reference to instance variable 'field1' needs "this.".'
    }

    static int staticField = 10;
    static void staticMethod() {}
}
