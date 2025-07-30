/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.FinalLocalVariableCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.finallocalvariable.classfieldtest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OutputClassFieldTest {
    private String instanceField = "instance";
    private int counter = 0;
    private List<String> dataList = new ArrayList<>();
    private Map<String, Object> cache = new HashMap<>();
    private boolean initialized = false;

    private static String staticField = "static";
    private static int globalCounter = 0;

    public void FieldsParametersLocalsTest(String name, int initialValue, boolean autoStart) {
        final String normalizedName = name.trim();
        final int validatedValue = Math.max(0, initialValue);
        final String logMessage = "Creating instance with: " + normalizedName;

        this.instanceField = normalizedName;
        this.counter = validatedValue;
        this.initialized = autoStart;
    }

    public void complexMethod(String param1, int param2, List<String> param3,
                              Map<String, Object> param4, boolean param5) {
        final String workingData = param1 + "_processed";
        final int calculatedSize = param2 * 2;
        final List<String> filteredList = new ArrayList<>(param3);
        final Map<String, Object> workingMap = new HashMap<>(param4);
        final boolean processFlag = param5 && !param3.isEmpty();
        final String statusMessage = "Processing " + calculatedSize + " items";
    }
}
