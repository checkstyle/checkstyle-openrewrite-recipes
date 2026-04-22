/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.imports.AvoidStarImportCheck">
    </module>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.avoidstarimport.inheritedinstancefieldchild;

import java.util.*; // violation 'Using the .* form of import should be avoided'
import org.checkstyle.autofix.recipe.avoidstarimport.inheritedinstancefieldparent.*; // violation 'Using the .* form of import should be avoided'

public class InputInheritedInstanceFieldChild extends InputInheritedInstanceFieldParent {
    private final List<String> values = new ArrayList<>();

    int size() {
        return value + values.size();
    }
}
