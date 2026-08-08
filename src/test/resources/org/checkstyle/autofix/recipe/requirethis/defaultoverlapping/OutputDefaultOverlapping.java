/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.RequireThisCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.requirethis.defaultoverlapping;

public class OutputDefaultOverlapping {
    int field1, field2, field3;

    OutputDefaultOverlapping(int field1) {
        this.field1 = field1;
        field2 = 0;
        foo(5);
    }

    void method2(int i) {
        foo(i);
    }

    void foo(int field3) {

        this.field3 = field3;
    }
}
