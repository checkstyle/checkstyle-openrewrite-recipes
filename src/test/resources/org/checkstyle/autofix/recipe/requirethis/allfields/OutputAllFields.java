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

public class OutputAllFields {
    int field1, field2, field3;
    String str = "hello";

    OutputAllFields(int field1) {
        this.field1 = field1;
        this.field2 = 0;
        foo(5);
    }

    void method2(int i) {
        foo(i);
        this.str.length();
    }

    void foo(int field3) {
        this.field3 = field3;
    }
}
