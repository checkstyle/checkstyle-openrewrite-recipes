/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.imports.RedundantImportCheck"/>
    <module name="com.puppycrawl.tools.checkstyle.checks.imports.AvoidStarImportCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.redundantimport.killmutation;

import java.lang.String; // violation
import java.util.*; // violation

public class InputKillMutation {
    List<String> x;
}
