/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.imports.AvoidStaticImportCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.avoidstaticimport.deeplynestedclassstaticimport;

public class OutputDeeplyNestedClassStaticImport {
    public static class A {
        public static class B {
        }
    }
    
    private InputDeeplyNestedClassStaticImport.A.B b;
}
