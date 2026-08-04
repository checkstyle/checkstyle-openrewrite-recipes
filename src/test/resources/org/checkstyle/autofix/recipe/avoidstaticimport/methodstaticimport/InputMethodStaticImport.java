/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.imports.AvoidStaticImportCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.avoidstaticimport.methodstaticimport;

import static java.util.Collections.sort; // violation 'Using a static member import should be avoided - java.util.Collections.sort.'
import java.util.List;

public class InputMethodStaticImport {
    void process(List<String> list) {
        sort(list);
        java.util.Collections.sort(list);
    }
}
