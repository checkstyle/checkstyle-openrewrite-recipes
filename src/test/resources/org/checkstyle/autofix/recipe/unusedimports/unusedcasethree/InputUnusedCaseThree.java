/*xml
<module name="Checker">
  <module name="com.puppycrawl.tools.checkstyle.filters.SuppressionXpathSingleFilter">
    <property name="checks" value="UnusedImportsCheck"/>
    <property name="query" value="//IMPORT/DOT[./IDENT[@text='List']]/DOT/IDENT[@text='java']"/>
  </module>
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.imports.UnusedImportsCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.unusedimports.unusedcasethree;

import java.util.List;
import java.util.Map; // violation 'Unused import - java.util.Map'

public class InputUnusedCaseThree {
}
