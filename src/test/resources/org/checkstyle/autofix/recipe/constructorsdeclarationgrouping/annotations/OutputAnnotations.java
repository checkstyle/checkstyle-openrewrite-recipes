/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.annotations;

/** Class with annotated constructors that are not grouped together. */
public class OutputAnnotations {

    @SuppressWarnings("unused")
    OutputAnnotations() {}

    OutputAnnotations(String s) {}

    @Deprecated
    OutputAnnotations(int x) {}

    void helper() {}

}
