/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.imports.AvoidStarImportCheck">
    </module>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.avoidstarimport.multifile1;

import java.io.*; // violation 'Using the .* form of import should be avoided'

public class InputMultiFile1 {
    File file;
}
