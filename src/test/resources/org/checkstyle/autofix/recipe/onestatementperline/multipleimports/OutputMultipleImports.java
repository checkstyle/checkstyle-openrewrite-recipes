/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.OneStatementPerLineCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.onestatementperline.multipleimports;

import java.util.ArrayList;
import java.util.List;

public class OutputMultipleImports {

    List<String> values = new ArrayList<>();
}
