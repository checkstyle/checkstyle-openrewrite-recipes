/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.RequireThisCheck">
      <property name="validateOnlyOverlapping" value="false"/>
    </module>
    <module name="com.puppycrawl.tools.checkstyle.filters.SuppressionSingleFilter">
      <property name="checks" value="RequireThis"/>
      <property name="files" value="OutputShadowedLocal.java"/>
    </module>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.requirethis.shadowedlocal;

import java.util.Map;
import java.util.function.Supplier;

public class InputShadowedLocal {
    private int config;
    private String errorMsg;
    public void validate(Map<String, String> connectorConfigs) {
        connectorConfigs.forEach((config, errorMsg) -> {
            Supplier<String> s = () -> {
                errorMsg.length(); // violation 'Reference to instance variable 'errorMsg' needs "this.".'
                return new String(config); // violation 'Reference to instance variable 'config' needs "this.".'
            };
        });
    }
}
