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
        String name = "John Doe";                          // violation, "should be declared final"
        int age = 25;                                      // violation, "should be declared final"
        double salary = 50000.0;                           // violation, "should be declared final"
        boolean isActive = true;                           // violation, "should be declared final"
        char grade = 'A';                                  // violation, "should be declared final"
        Object data = new HashMap<>();                     // violation, "should be declared final"
        List<String> items = new ArrayList<>();            // violation, "should be declared final"
        StringBuilder builder = new StringBuilder();       // violation, "should be declared final"
        Date currentDate = new Date();                     // violation, "should be declared final"
        File tempFile = new File("temp.txt");     // violation, "should be declared final"
    }

    public void methodWithCalculations() {
        int x = 10;                                        // violation, "should be declared final"
        int y = 20;                                        // violation, "should be declared final"
        int sum = x + y;                                   // violation, "should be declared final"
        int product = x * y;                               // violation, "should be declared final"
        double average = (x + y) / 2.0;                    // violation, "should be declared final"
        // violation below, "should be declared final"
        String result = "Sum: " + sum + ", Product: " + product;
        boolean isPositive = sum > 0;                      // violation, "should be declared final"
    }

    public String processResult(String input) {
        String trimmed = input.trim();                     // violation, "should be declared final"
        String upperCase = trimmed.toUpperCase();          // violation, "should be declared final"
        String formatted = "[" + upperCase + "]";          // violation, "should be declared final"
        return formatted;
    }

}
