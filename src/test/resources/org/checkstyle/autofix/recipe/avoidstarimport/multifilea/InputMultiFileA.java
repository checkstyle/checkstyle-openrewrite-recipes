/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.imports.AvoidStarImportCheck">
      <property name="excludes" value="java.util.concurrent,java.util"/>
    </module>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.avoidstarimport.multifilea;

import java.io.*; // violation 'Using the .* form of import should be avoided'

public class InputMultiFileA {
    File file;
    java.util.Date date;
}
