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
public class OutputAnnotations {

    @SuppressWarnings("unused")
    OutputAnnotations() {}

    OutputAnnotations(String s) {}

    @Deprecated
    OutputAnnotations(int x) {}

    void helper() {}

}
