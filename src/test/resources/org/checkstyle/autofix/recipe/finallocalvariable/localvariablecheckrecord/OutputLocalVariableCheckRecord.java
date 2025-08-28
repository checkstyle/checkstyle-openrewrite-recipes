/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.FinalLocalVariableCheck">
    </module>
  </module>
</module>

*/
package org.checkstyle.autofix.recipe.finallocalvariable.localvariablecheckrecord;

public class OutputLocalVariableCheckRecord {
    record bad(int i) {
        public bad {
            final int b = 0;
        }
    }
}
