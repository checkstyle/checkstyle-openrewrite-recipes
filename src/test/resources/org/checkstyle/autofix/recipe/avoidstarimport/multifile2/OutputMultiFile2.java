/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.imports.AvoidStarImportCheck">
      <property name="excludes" value="java.util"/>
    </module>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.avoidstarimport.multifile2;

import java.util.*;

public class OutputMultiFile2 {
    List<String> list = new ArrayList<>();
}
