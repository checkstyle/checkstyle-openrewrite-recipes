/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.AvoidNoArgumentSuperConstructorCallCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.avoidnoargumentsuperconstructorcall.simplesupercall;

import java.util.ArrayList;

public class OutputSimpleSuperCall extends ArrayList<Object> {
    public OutputSimpleSuperCall() { System.out.println();
    }

    public OutputSimpleSuperCall(int x) {
        super(x);
    }
}
