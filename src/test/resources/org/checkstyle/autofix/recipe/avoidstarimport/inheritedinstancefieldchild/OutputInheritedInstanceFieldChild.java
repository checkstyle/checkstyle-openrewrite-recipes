/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.imports.AvoidStarImportCheck">
    </module>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.avoidstarimport.inheritedinstancefieldchild;

import org.checkstyle.autofix.recipe.avoidstarimport.inheritedinstancefieldparent.InputInheritedInstanceFieldParent;

import java.util.ArrayList;
import java.util.List;

public class OutputInheritedInstanceFieldChild extends InputInheritedInstanceFieldParent {
    private final List<String> values = new ArrayList<>();

    int size() {
        return value + values.size();
    }
}
