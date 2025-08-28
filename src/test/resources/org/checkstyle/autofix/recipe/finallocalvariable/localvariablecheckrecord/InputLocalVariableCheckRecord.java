/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.FinalLocalVariableCheck">
    </module>
  </module>
</module>

*/
package org.checkstyle.autofix.recipe.finallocalvariable.localvariablecheckrecord;

public class InputLocalVariableCheckRecord {
    record bad(int i) {
        public bad {
            int b = 0; // violation, "Variable 'b' should be declared final"
        }
    }
}
