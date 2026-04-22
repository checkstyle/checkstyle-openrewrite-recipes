/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.imports.AvoidStarImportCheck">
    </module>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.avoidstarimport.multiplerequiredstarimports;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.MAX_VALUE;
import static java.time.Instant.EPOCH;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonMap;

public class OutputMultipleRequiredStarImports {
    private final List<Duration> durations = emptyList();
    private final Map<String, Instant> values = singletonMap("epoch", EPOCH);
    private final int maxValue = MAX_VALUE;
}
