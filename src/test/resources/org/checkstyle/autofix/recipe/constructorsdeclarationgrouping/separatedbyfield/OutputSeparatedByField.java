/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.separatedbyfield;

/**
 * A single constructor is separated from the first constructor by a field declaration.
 * Verifies the minimal field-separation scenario.
 */
public class OutputSeparatedByField {

    OutputSeparatedByField() {}

    OutputSeparatedByField(int x) {}

    int x;

}
