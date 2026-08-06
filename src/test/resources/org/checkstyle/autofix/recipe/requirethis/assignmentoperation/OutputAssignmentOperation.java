/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.RequireThisCheck">
      <property name="validateOnlyOverlapping" value="true"/>
    </module>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.requirethis.assignmentoperation;

public class OutputAssignmentOperation {
    private String config;

    void validate(String config) {
        this.config += "x";
    }
}
