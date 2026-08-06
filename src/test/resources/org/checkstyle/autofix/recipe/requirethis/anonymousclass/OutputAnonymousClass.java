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

public class OutputAnonymousClass {
    void foo() {
        Runnable r = new Runnable() {
            private int myField;
            @Override
            public void run() {
                this.myField = 5;
            }
        };
    }
}
