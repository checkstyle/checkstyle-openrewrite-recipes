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

import java.util.Collections;
import java.util.List;

import static java.lang.Math.PI;

public class OutputFieldStaticImport {
    List list = Collections.EMPTY_LIST;
    double d = PI;
    List list2 = Collections.EMPTY_LIST;

    void foo(java.time.DayOfWeek e) {
        switch (e) {
            case MONDAY:
                break;
        }
    }
}
