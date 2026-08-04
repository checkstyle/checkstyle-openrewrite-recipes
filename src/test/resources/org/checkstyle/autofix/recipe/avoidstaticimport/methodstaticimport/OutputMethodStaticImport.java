/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.imports.AvoidStaticImportCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.avoidstaticimport.methodstaticimport;
import java.util.Collections;
import java.util.List;

public class OutputMethodStaticImport {
    void process(List<String> list) {
        Collections.sort(list);
        Collections.sort(list);
    }
}
