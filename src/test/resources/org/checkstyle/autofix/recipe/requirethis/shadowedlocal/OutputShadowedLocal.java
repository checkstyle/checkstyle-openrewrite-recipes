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

public class OutputShadowedLocal {
    private int config;
    private String errorMsg;
    public void validate(Map<String, String> connectorConfigs) {
        connectorConfigs.forEach((config, errorMsg) -> {
            Supplier<String> s = () -> {
                this.errorMsg.length();
                return new String(config);
            };
        });
    }
}
