/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.imports.AvoidStarImportCheck"/>
    <module name="com.puppycrawl.tools.checkstyle.checks.imports.UnusedImportsCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.avoidstarimport.killmutation1;

import java.io.*; // violation
import java.io.File; // violation

public class InputKillMutation1 {
    java.io.File f;
}
