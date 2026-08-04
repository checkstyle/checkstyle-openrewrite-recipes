/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.imports.AvoidStaticImportCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.avoidstaticimport.nestedclassstaticimport;

import java.util.Map;

public class OutputNestedClassStaticImport {
    void test(Map<String, String> map) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
        }
    }
}
