/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.imports.AvoidStarImportCheck">
    </module>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.avoidstarimport.killmutation2;

import org.checkstyle.autofix.recipe.avoidstarimport.killmutation2supportclass.InputKillMutation2SupportClass;

import static org.checkstyle.autofix.recipe.avoidstarimport.killmutation2supportclass.InputKillMutation2SupportClass.STATIC_CONSTANT;

public class OutputKillMutation2 {

    int value = STATIC_CONSTANT;

    InputKillMutation2SupportClass obj;

    int instanceValue() {
        return obj.instanceField;
    }

    int maxInt = Integer.MAX_VALUE;

    double absValue = Math.abs(-1.0);
}
