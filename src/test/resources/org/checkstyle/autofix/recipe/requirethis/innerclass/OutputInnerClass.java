/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.RequireThisCheck">
      <property name="validateOnlyOverlapping" value="false"/>
    </module>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.requirethis.innerclass;

import java.util.Iterator;
import java.util.List;

public class OutputInnerClass {
    class StructRegistry {
        private List<String> commonStructNames;
        private String structField;

        class NamedInner {
            void foo() {
                StructRegistry.this.structField = "test";
            }
        }

        Iterator<String> commonStructs() {
            return new Iterator<String>() {
                private final Iterator<String> iter = StructRegistry.this.commonStructNames.iterator();

                @Override public boolean hasNext() {
                    StructRegistry.this.structField = "test";
                    return this.iter.hasNext();
                }

                @Override public String next() {
                    return this.iter.next();
                }
            };
        }
    }
}
