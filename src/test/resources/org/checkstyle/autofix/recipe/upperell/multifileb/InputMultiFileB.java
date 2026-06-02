/*xml
<module name="Checker">
  <module name="com.puppycrawl.tools.checkstyle.filters.SuppressionSingleFilter">
    <property name="checks" value="UpperEll"/>
    <property name="files" value=".*MultiFileB\.java"/>
  </module>
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.UpperEllCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.upperell.multifileb;
public class InputMultiFileB {
    long a = 1l;
}
