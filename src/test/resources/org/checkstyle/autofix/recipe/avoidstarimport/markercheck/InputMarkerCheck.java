/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.imports.AvoidStarImportCheck">
      <property name="allowStaticMemberImports" value="true"/>
      <property name="excludes" value="java.io"/>
    </module>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.avoidstarimport.markercheck;

import java.io.*; import java.util.*; // violation 'Using the .* form of import should be avoided'

public class InputMarkerCheck {
    List<String> list;
    java.io.File file;
}
