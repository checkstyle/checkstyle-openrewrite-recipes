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

public class OutputFieldsAndMethods {
    int field1, field2, field3;

    OutputFieldsAndMethods(int field1) {
        this.field1 = field1;
        this.field2 = 0;
        this.foo(5);
    }

    void method2(int i) {
        this.foo(i);
        this.foo(
            this.field1);
        staticMethod();
        int j = staticField;
    }

    void foo(int field3) {
        this.field3 = field3;
    }

    int getField1() {
        return this.field1;
    }

    static int staticField = 10;
    static void staticMethod() {}
}
