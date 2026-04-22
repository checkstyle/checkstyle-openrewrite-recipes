/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.imports.AvoidStarImportCheck">
    </module>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.avoidstarimport.nonstaticfieldandlocalvariablereferences;

import java.util.ArrayList;
import java.util.List;

public class OutputNonStaticFieldAndLocalVariableReferences {
    private final List<String> values = new ArrayList<>();
    private int count;

    int size() {
        final int localCount = values.size();
        final Holder holder = new Holder();
        count = localCount;
        return count + holder.value;
    }

    private static final class Holder {
        private int value;
    }
}
