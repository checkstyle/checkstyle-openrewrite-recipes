/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.imports.AvoidStaticImportCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.avoidstaticimport.fieldclassstaticimport;

import java.util.Map;

public class OutputFieldClassStaticImport {
    private Map.Entry<String, String> entry;
}
