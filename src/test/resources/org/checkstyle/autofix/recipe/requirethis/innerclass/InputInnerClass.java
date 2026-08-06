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

public class InputInnerClass {
    class StructRegistry {
        private List<String> commonStructNames;
        private String structField;

        class NamedInner {
            void foo() {
                structField = "test"; // violation 'Reference to instance variable 'structField' needs "StructRegistry.this.".'
            }
        }

        Iterator<String> commonStructs() {
            return new Iterator<String>() {
                private final Iterator<String> iter = commonStructNames.iterator(); // violation 'Reference to instance variable 'commonStructNames' needs "this.".'

                @Override public boolean hasNext() {
                    structField = "test"; // violation 'Reference to instance variable 'structField' needs "StructRegistry.this.".'
                    return iter.hasNext(); // violation 'Reference to instance variable 'iter' needs "this.".'
                }

                @Override public String next() {
                    return iter.next(); // violation 'Reference to instance variable 'iter' needs "this.".'
                }
            };
        }
    }
}
