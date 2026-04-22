/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.imports.AvoidStarImportCheck">
    </module>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.avoidstarimport.multiplerequiredstarimports;

import java.time.*; // violation 'Using the .* form of import should be avoided'
import java.util.*; // violation 'Using the .* form of import should be avoided'

import static java.lang.Integer.*; // violation 'Using the .* form of import should be avoided'
import static java.time.Instant.*; // violation 'Using the .* form of import should be avoided'
import static java.util.Collections.*; // violation 'Using the .* form of import should be avoided'

public class InputMultipleRequiredStarImports {
    private final List<Duration> durations = emptyList();
    private final Map<String, Instant> values = singletonMap("epoch", EPOCH);
    private final int maxValue = MAX_VALUE;
}
