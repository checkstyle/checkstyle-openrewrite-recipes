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
        var autoString = "inferred string";
        var autoNumber = 42;
        var autoList = new ArrayList<String>();
        var autoMap = new HashMap<String, Integer>();

        String regularString = "explicit type";
        var anotherAuto = "mixed with regular";
        int regularInt = 100;
    }

    public void loopVariables() {
        List<String> items = Arrays.asList("a", "b", "c");

        for (String item : items) {
            System.out.println(item);
            String processed = item.toUpperCase();
        }

        for (int i = 0; i < 10; i++) {
            String loopVar = "iteration " + i;
            int doubled = i * 2;
        }

        int counter = 0;
        while (counter < 5) {
            String message = "Count: " + counter;
            counter++;
        }
    }

    public void lambdaExpressions() {
        List<String> items = Arrays.asList("one", "two", "three");

        items.forEach(item -> System.out.println(item));
        items.stream().map(item -> item.toUpperCase()).forEach(System.out::println);

        items.forEach((String item) -> {
            String processed = item.trim();
            System.out.println(processed);
        });

        String prefix = "Item: ";
        items.forEach(item -> System.out.println(prefix + item));
    }

    public void tryWithResourcesAndExceptions() {
        String filename = "test.txt";

        try (FileReader reader = new FileReader(filename);
             BufferedReader buffered = new BufferedReader(reader)) {

            String line = buffered.readLine();
            String processed = line != null ? line.trim() : "";

        } catch (IOException e) {
            String errorMsg = "Error reading file: " + e.getMessage();
            System.err.println(errorMsg);

        }
    }
}
