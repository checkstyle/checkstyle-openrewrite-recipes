/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.OneStatementPerLineCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.onestatementperline.multipleimports;

import java.util.ArrayList; import java.util.List; // violation 'Only one statement per line allowed.'

public class InputMultipleImports {

    List<String> values = new ArrayList<>();
}
