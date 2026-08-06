/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.RequireThisCheck">
      <property name="validateOnlyOverlapping" value="false"/>
    </module>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.requirethis.anonymousclassouterfield;

public class OutputAnonymousClassOuterField {
    class Wrapper {
        private int field;
        
        void foo() {
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    Wrapper.this.field = 5;
                }
            };
        }
    }
}
