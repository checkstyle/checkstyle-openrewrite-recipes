/*xml
<module name="Checker">
  <module name="com.puppycrawl.tools.checkstyle.filters.SuppressionSingleFilter">
    <property name="checks" value="AvoidStarImport"/>
    <property name="files" value="Output.*\.java"/>
  </module>
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.imports.AvoidStaticImportCheck">
      <property name="excludes" value="java.lang.Math.*"/>
    </module>
    <module name="com.puppycrawl.tools.checkstyle.checks.imports.AvoidStarImportCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.avoidstaticimport.fieldstaticimport;

import static java.util.Collections.EMPTY_LIST; // violation 'Using a static member import should be avoided - java.util.Collections.EMPTY_LIST.'
import static java.lang.Math.*; // violation 'Using the '.*' form of import should be avoided - java.lang.Math.*.'
import java.util.List;
import static java.time.DayOfWeek.MONDAY; // violation 'Using a static member import should be avoided - java.time.DayOfWeek.MONDAY.'

public class InputFieldStaticImport {
    List list = EMPTY_LIST;
    double d = PI;
    List list2 = java.util.Collections.EMPTY_LIST;

    void foo(java.time.DayOfWeek e) {
        switch (e) {
            case MONDAY:
                break;
        }
    }
}
