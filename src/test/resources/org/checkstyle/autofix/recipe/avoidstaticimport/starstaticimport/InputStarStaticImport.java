/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.imports.AvoidStaticImportCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.avoidstaticimport.starstaticimport;

import static java.util.Collections.*; // violation 'Using a static member import should be avoided - java.util.Collections.*.'
import java.util.List;

public class InputStarStaticImport {
    void process(List<String> list) {
        sort(list);
    }
}
