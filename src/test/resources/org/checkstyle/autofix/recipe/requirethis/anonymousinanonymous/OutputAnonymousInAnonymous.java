/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.RequireThisCheck">
      <property name="validateOnlyOverlapping" value="false"/>
    </module>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.requirethis.anonymousinanonymous;

public class OutputAnonymousInAnonymous {
    class Wrapper {
        private int buffer;
        
        void foo() {
            Runnable r1 = new Runnable() {
                @Override
                public void run() {
                    Runnable r2 = new Runnable() {
                        @Override
                        public void run() {
                            Wrapper.this.buffer = 5;
                        }
                    };
                }
            };
        }
    }
}
