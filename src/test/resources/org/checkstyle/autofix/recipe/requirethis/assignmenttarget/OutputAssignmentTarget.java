/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.RequireThisCheck">
      <property name="validateOnlyOverlapping" value="false"/>
    </module>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.requirethis.assignmenttarget;

public class OutputAssignmentTarget {
    private String config;

    String validate(String config) {
        this.config = "test";
        return config;
    }
}
