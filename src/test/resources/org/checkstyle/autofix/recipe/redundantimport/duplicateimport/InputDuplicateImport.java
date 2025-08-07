/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.imports.RedundantImportCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.redundantimport.duplicateimport;

import static java.util.Arrays.asList;
import static java.util.Arrays.asList; // violation 'Duplicate import'

import java.util.List;
import java.util.List; // violation 'Duplicate import'
import java.util.Set;

public class InputDuplicateImport {}