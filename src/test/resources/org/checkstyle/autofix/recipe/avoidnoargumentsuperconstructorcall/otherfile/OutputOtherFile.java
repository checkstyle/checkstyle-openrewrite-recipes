/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.AvoidNoArgumentSuperConstructorCallCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.avoidnoargumentsuperconstructorcall.otherfile;

import java.util.ArrayList;

public class OutputOtherFile extends ArrayList<Object> {
    public OutputOtherFile() {
        super(1);
    }

    public OutputOtherFile(int x) {
        super(x);
    }
}
