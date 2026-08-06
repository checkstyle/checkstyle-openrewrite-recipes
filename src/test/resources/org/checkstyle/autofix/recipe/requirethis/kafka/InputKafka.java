/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.RequireThisCheck">
      <property name="validateOnlyOverlapping" value="false"/>
    </module>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.requirethis.kafka;

import java.util.Iterator;

public class InputKafka {
    public Iterator<String> iterator() {
        return new Iterator<String>() {
            private String buffer = "test";

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public String next() {
                String str = new String(buffer); // violation 'Reference to instance variable 'buffer' needs "this.".'
                return str;
            }
        };
    }
}
