/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.imports.UnusedImportsCheck"/>
    <module name="com.puppycrawl.tools.checkstyle.checks.imports.AvoidStarImportCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.unusedimports.killmutation;

import java.io.File; // violation
import java.util.*; // violation

public class InputKillMutation {
    List<String> x;
}
