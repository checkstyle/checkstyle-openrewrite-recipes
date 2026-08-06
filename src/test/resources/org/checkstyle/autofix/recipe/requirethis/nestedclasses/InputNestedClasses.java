/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.RequireThisCheck">
      <property name="validateOnlyOverlapping" value="false"/>
    </module>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.requirethis.nestedclasses;

public class InputNestedClasses {
    class Nested1 {
        int outerField;
        class Nested2 {
            void foo() {
                outerField = 5; // violation 'Reference to instance variable 'outerField' needs "Nested1.this.".'
            }
        }
    }
}
