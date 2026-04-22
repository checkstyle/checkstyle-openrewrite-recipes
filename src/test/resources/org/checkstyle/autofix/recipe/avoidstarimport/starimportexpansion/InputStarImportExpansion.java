/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.imports.AvoidStarImportCheck">
      <property name="excludes" value="java.io"/>
    </module>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.avoidstarimport.starimportexpansion;

import java.io.*;
import java.util.*; // violation 'Using the .* form of import should be avoided'

public class InputStarImportExpansion {
    List<String> list = new ArrayList<>();
    File file;
    Thread thread;
}
