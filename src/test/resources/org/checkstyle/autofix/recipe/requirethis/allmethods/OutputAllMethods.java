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

public class OutputAllMethods {
    int field1, field2, field3;

    OutputAllMethods(int field1) {
        this.field1 = field1;
        field2 = 0;
        this.foo(5);
    }

    void method2(int i) {
        this.foo(i);
    }

    void foo(int field3) {
        field3 = field3;
    }
}
