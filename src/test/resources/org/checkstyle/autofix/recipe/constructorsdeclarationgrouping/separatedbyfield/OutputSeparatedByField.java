/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.separatedbyfield;

/** Class where constructors are separated by a field declaration. */
public class OutputSeparatedByField {

    OutputSeparatedByField() {}

    OutputSeparatedByField(int x) {}

    int x;

}
