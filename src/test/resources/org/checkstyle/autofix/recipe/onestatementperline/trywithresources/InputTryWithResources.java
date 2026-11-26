/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.OneStatementPerLineCheck">
      <property name="treatTryResourcesAsStatement" value="true"/>
    </module>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.onestatementperline.trywithresources;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class InputTryWithResources {

    void test() throws Exception {
        try (InputStream in1 = new ByteArrayInputStream(new byte[0]); InputStream in2 = new ByteArrayInputStream(new byte[0])) { // violation 'Only one statement per line allowed.'
        }
    }
}
