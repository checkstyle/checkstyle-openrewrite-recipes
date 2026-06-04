/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.imports.RedundantImportCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.redundantimport.multifile;

import java.lang.String; // violation 'Redundant import from the java.lang package'

class InputMultiFileB {
}
