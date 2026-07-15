/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.imports.AvoidStarImportCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.avoidstarimport.primitiveclassaccess;

import java.util.ArrayList;
import java.util.List;

public class OutputPrimitiveClassAccess {
    public void method() {
        String clazz1 = int.class.getName();
        String clazz2 = double.class.getName();
        List<String> list = new ArrayList<>();
    }
}
