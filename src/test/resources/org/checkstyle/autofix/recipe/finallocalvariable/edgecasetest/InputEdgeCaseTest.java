/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.FinalLocalVariableCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.finallocalvariable.edgecasetest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class InputEdgeCaseTest {
    public void varDeclarations() {
        var autoString = "inferred string";                 // violation, "should be declared final"
        var autoNumber = 42;                                // violation, "should be declared final"
        var autoList = new ArrayList<String>();             // violation, "should be declared final"
        var autoMap = new HashMap<String, Integer>();       // violation, "should be declared final"

        String regularString = "explicit type";             // violation, "should be declared final"
        var anotherAuto = "mixed with regular";             // violation, "should be declared final"
        int regularInt = 100;                               // violation, "should be declared final"
    }

    public void loopVariables() {
        List<String> items = Arrays.asList("a", "b", "c");  // violation, "should be declared final"

        for (String item : items) {
            System.out.println(item);
            String processed = item.toUpperCase();          // violation, "should be declared final"
        }

        for (int i = 0; i < 10; i++) {
            String loopVar = "iteration " + i;              // violation, "should be declared final"
            int doubled = i * 2;                            // violation, "should be declared final"
        }

        int counter = 0;
        while (counter < 5) {
            String message = "Count: " + counter;           // violation, "should be declared final"
            counter++;
        }
    }

    public void lambdaExpressions() {
        // violation below, "should be declared final"
        List<String> items = Arrays.asList("one", "two", "three");

        items.forEach(item -> System.out.println(item));
        items.stream().map(item -> item.toUpperCase()).forEach(System.out::println);

        items.forEach((String item) -> {
            String processed = item.trim();  // violation, "should be declared final"
            System.out.println(processed);
        });

        String prefix = "Item: ";            // violation, "should be declared final"
        items.forEach(item -> System.out.println(prefix + item));
    }

    public void tryWithResourcesAndExceptions() {
        String filename = "test.txt";        // violation, "should be declared final"

        try (FileReader reader = new FileReader(filename);
             BufferedReader buffered = new BufferedReader(reader)) {

            String line = buffered.readLine();   // violation, "should be declared final"
            // violation below, "should be declared final"
            String processed = line != null ? line.trim() : "";

        } catch (IOException e) {
            // violation below, "should be declared final"
            String errorMsg = "Error reading file: " + e.getMessage();
            System.err.println(errorMsg);

        }
    }
}
