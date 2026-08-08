/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.imports.AvoidStaticImportCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.avoidstaticimport.variabledeclarationstaticimport;

import static java.util.Collections.EMPTY_LIST; // violation 'Using a static member import should be avoided - java.util.Collections.EMPTY_LIST.'

public class InputVariableDeclarationStaticImport {
    void test() {
        int EMPTY_LIST = 5;
    }
}
