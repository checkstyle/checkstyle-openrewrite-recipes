/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.imports.AvoidStaticImportCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.avoidstaticimport.nestedclassstaticimport;

import static java.util.Map.Entry; // violation 'Using a static member import should be avoided - java.util.Map.Entry.'

import java.util.Map;

public class InputNestedClassStaticImport {
    void test(Map<String, String> map) {
        for (Entry<String, String> entry : map.entrySet()) {
        }
    }
}
