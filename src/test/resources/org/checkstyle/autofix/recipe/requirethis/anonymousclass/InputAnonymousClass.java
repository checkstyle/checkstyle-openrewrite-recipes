/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.RequireThisCheck">
      <property name="validateOnlyOverlapping" value="false"/>
    </module>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.requirethis.anonymousclass;

public class InputAnonymousClass {
    void foo() {
        Runnable r = new Runnable() {
            private int myField;
            @Override
            public void run() {
                myField = 5; // violation 'Reference to instance variable 'myField' needs "this.".'
            }
        };
    }
}
