/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.AvoidNoArgumentSuperConstructorCallCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.avoidnoargumentsuperconstructorcall.otherfile;

import java.util.ArrayList;

public class InputOtherFile extends ArrayList<Object> {
    public InputOtherFile() {
        super(1);
    }

    public InputOtherFile(int x) {
        super(x);
    }
}
