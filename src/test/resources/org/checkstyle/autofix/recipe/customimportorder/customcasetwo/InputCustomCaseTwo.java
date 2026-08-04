/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.imports.CustomImportOrderCheck">
      <property name="customImportOrderRules" value="STATIC###STANDARD_JAVA_PACKAGE###SPECIAL_IMPORTS###THIRD_PARTY_PACKAGE###SAME_PACKAGE(3)"/>
      <property name="separateLineBetweenGroups" value="true"/>
      <property name="specialImportsRegExp" value="^org\.junit\."/>
    </module>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.customimportorder.customcasetwo;

import org.checkstyle.autofix.recipe.customimportorder.customcasetwo.SamePackageClass;
import com.google.common.collect.ImmutableList; // violation
import org.junit.jupiter.api.Test; // violation
import java.util.List; // violation
import static java.util.Collections.emptyList; // violation

public class InputCustomCaseTwo {
}
