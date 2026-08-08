/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.AvoidNoArgumentSuperConstructorCallCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.avoidnoargumentsuperconstructorcall.simplesupercall;

import java.util.ArrayList;

public class InputSimpleSuperCall extends ArrayList<Object> {
    public InputSimpleSuperCall() {
        super(); System.out.println(); // violation 'Unnecessary call to superclass constructor with no arguments.'
    }

    public InputSimpleSuperCall(int x) {
        super(x);
    }
}
