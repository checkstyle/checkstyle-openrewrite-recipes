/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.imports.AvoidStarImportCheck">
      <property name="excludes" value="java.lang.Math"/>
    </module>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.avoidstarimport.staticstarimportexpansion;

import java.util.List;
import java.util.Calendar;

import static java.lang.Math.*;
import static java.util.Collections.*; // violation 'Using the .* form of import should be avoided'
import static java.util.Calendar.*; // violation 'Using the .* form of import should be avoided'

public class InputStaticStarImportExpansion {
    boolean b = emptyList().isEmpty();
    double d = abs(-1.0);
    long time = System.currentTimeMillis();
    Calendar cal = getInstance();
    long time2 = cal.getTimeInMillis();
    
    java.util.GregorianCalendar gc = new java.util.GregorianCalendar() {
        void foo() {
            getTimeInMillis();
        }
    };
}
