/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.imports.UnusedImportsCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.unusedimports.unusedcasetwo;

import java.io.*;
import java.lang.*;
import java.lang.String; // violation 'Unused import - java.lang.String.'

import java.util.List;
import java.lang.*;

public class InputUnusedCaseTwo {
  File[] files;
  List<Integer> list;
}
