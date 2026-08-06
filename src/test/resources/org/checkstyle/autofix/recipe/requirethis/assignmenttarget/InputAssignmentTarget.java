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

public class InputAssignmentTarget {
    private String config;

    String validate(String config) {
        config = "test"; // violation 'Reference to instance variable 'config' needs "this.".'
        return config;
    }
}
