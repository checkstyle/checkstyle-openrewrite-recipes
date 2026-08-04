/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.imports.CustomImportOrderCheck">
      <property name="sortImportsInGroupAlphabetically" value="true"/>
    </module>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.customimportorder.customcaseone;

import java.util.List;
import java.util.ArrayList; // violation 'Wrong lexicographical order for 'java.util.ArrayList' import. Should be before 'java.util.List'.'

public class InputCustomCaseOne {
}
