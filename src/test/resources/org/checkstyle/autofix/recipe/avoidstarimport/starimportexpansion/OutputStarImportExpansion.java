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
import java.util.ArrayList;
import java.util.List;

public class OutputStarImportExpansion {
    List<String> list = new ArrayList<>();
    File file;
    Thread thread;
}
