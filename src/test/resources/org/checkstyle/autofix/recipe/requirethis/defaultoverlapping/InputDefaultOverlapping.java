/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.RequireThisCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.requirethis.defaultoverlapping;

public class InputDefaultOverlapping {
    int field1, field2, field3;

    InputDefaultOverlapping(int field1) {
        this.field1 = field1;
        field2 = 0;
        foo(5);
    }

    void method2(int i) {
        foo(i);
    }

    void foo(int field3) {

        field3 = field3; // violation 'Reference to instance variable 'field3' needs "this.".'
    }
}
