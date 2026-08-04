/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.imports.AvoidStaticImportCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.avoidstaticimport.deeplynestedclassstaticimport;

import static org.checkstyle.autofix.recipe.avoidstaticimport.deeplynestedclassstaticimport.InputDeeplyNestedClassStaticImport.A.B; // violation 'Using a static member import should be avoided'

public class InputDeeplyNestedClassStaticImport {
    public static class A {
        public static class B {
        }
    }
    
    private B b;
}
