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

public class InputSingleLocalTest {
    public void basicLocalVariables() {
        String name = "John Doe";
        int age = 25;
        double salary = 50000.0;
        boolean isActive = true;
        char grade = 'A';
        Object data = new HashMap<>();
        List<String> items = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        Date currentDate = new Date();
        File tempFile = new File("temp.txt");
    }

    public void methodWithCalculations() {
        int x = 10;
        int y = 20;
        int sum = x + y;
        int product = x * y;
        double average = (x + y) / 2.0;
        String result = "Sum: " + sum + ", Product: " + product;
        boolean isPositive = sum > 0;
    }

    public String processResult(String input) {
        String trimmed = input.trim();
        String upperCase = trimmed.toUpperCase();
        String formatted = "[" + upperCase + "]";
        return formatted;
    }

}
