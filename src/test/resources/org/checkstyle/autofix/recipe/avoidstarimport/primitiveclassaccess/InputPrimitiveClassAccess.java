/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.imports.AvoidStarImportCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.avoidstarimport.primitiveclassaccess;

import java.util.*; // violation 'Using the .* form of import should be avoided'

public class InputPrimitiveClassAccess {
    public void method() {
        String clazz1 = int.class.getName();
        String clazz2 = double.class.getName();
        List<String> list = new ArrayList<>();
    }
}
