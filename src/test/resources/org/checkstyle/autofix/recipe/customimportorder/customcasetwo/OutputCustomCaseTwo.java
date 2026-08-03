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

import static java.util.Collections.emptyList;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;

import org.checkstyle.autofix.recipe.customimportorder.customcasetwo.SamePackageClass;

public class OutputCustomCaseTwo {
}
