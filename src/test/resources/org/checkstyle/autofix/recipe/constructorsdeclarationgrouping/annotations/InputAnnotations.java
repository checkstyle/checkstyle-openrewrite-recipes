/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.annotations;

/**
 * Constructors carrying annotations ({@code @SuppressWarnings}, {@code @Deprecated})
 * are separated by a method. Verifies that annotations are preserved correctly after
 * moving.
 */
public class InputAnnotations {

    @SuppressWarnings("unused")
    InputAnnotations() {}

    InputAnnotations(String s) {}

    void helper() {}

    @Deprecated  // violation 'Constructors should be grouped together'
    InputAnnotations(int x) {}

}
