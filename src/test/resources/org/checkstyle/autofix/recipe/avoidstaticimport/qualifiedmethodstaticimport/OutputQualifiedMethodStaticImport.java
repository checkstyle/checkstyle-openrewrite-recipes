/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.imports.AvoidStaticImportCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.avoidstaticimport.qualifiedmethodstaticimport;

public class OutputQualifiedMethodStaticImport {
    void test() {
        int a = Math.max(1, 2);
        int b = Math.max(1, 2);
        int c = java.lang.Math.max(1, 2);
        test();
    }
}
