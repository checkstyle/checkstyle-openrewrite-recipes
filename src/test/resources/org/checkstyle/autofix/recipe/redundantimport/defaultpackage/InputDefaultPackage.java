/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.imports.RedundantImportCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.redundantimport.defaultpackage;

import java.util.List;
import java.util.List; // violation 'Duplicate import'

class InputDefaultPackage {
}
