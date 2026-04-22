/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.imports.AvoidStarImportCheck">
      <property name="excludes" value="java.util.concurrent"/>
    </module>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.avoidstarimport.multifileb;

import java.util.concurrent.*;

public class InputMultiFileB {
    java.util.concurrent.TimeUnit unit;
}
