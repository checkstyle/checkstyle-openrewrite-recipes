/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.FinalLocalVariableCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.finallocalvariable.singlelocaltest;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class OutputSingleLocalTest {
    public void basicLocalVariables() {
        final String name = "John Doe";
        final int age = 25;
        final double salary = 50000.0;
        final boolean isActive = true;
        final char grade = 'A';
        final Object data = new HashMap<>();
        final List<String> items = new ArrayList<>();
        final StringBuilder builder = new StringBuilder();
        final Date currentDate = new Date();
        final File // prefix comment preceding target element
        tempFile = new File("temp.txt");
    }

    public void methodWithCalculations() {
        final int x = 10;
        final int y = 20;
        final int sum = x + y;
        final int product = x * y;
        final double average = (x + y) / 2.0;
        final String result = "Sum: " + sum + ", Product: " + product;
        final boolean isPositive = sum > 0;
    }

    public String processResult(String input) {
        final String trimmed = input.trim();
        final String upperCase = trimmed.toUpperCase();
        final String formatted = "[" + upperCase + "]";
        return formatted;
    }

}
