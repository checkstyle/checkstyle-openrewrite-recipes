/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.imports.AvoidStaticImportCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.avoidstaticimport.qualifiedmethodstaticimport;

import static java.lang.Math.max; // violation 'Using a static member import should be avoided - java.lang.Math.max.'

public class InputQualifiedMethodStaticImport {
    void test() {
        int a = Math.max(1, 2);
        int b = max(1, 2);
        int c = java.lang.Math.max(1, 2);
        test();
    }
}
