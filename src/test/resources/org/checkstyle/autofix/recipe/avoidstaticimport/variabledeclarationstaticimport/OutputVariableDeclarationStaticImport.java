/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.imports.AvoidStaticImportCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.avoidstaticimport.variabledeclarationstaticimport;

public class OutputVariableDeclarationStaticImport {
    void test() {
        int EMPTY_LIST = 5;
    }
}
