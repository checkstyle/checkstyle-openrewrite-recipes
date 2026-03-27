/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.annotations;

/** Class with annotated constructors that are not grouped together. */
public class InputAnnotations {

    @SuppressWarnings("unused")
    InputAnnotations() {}

    InputAnnotations(String s) {}

    void helper() {}

    @Deprecated  // violation 'Constructors should be grouped together'
    InputAnnotations(int x) {}

}
