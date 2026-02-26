/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.imports.UnusedImportsCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.unusedimports.unusedcasetwo;

import java.io.File; // violation 'Unused import - java.io.File.'

import static java.io.File. // violation 'Unused import - java.io.File.listRoots.'
    listRoots;

import java.util.List; // violation 'Unused import - java.util.List.'

public class InputUnusedCaseTwo {
}
