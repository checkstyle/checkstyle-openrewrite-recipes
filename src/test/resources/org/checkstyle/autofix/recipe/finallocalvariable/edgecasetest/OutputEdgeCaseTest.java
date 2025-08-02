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

public class OutputEdgeCaseTest {
    public void varDeclarations() {
        final var autoString = "inferred string";
        final var autoNumber = 42;
        final var autoList = new ArrayList<String>();
        final var autoMap = new HashMap<String, Integer>();

        final String regularString = "explicit type";
        final var anotherAuto = "mixed with regular";
        final int regularInt = 100;
    }

    public void loopVariables() {
        final List<String> items = Arrays.asList("a", "b", "c");

        for (String item : items) {
            System.out.println(item);
            final String processed = item.toUpperCase();
        }

        for (int i = 0; i < 10; i++) {
            final String loopVar = "iteration " + i;
            final int doubled = i * 2;
        }

        int counter = 0;
        while (counter < 5) {
            final String message = "Count: " + counter;
            counter++;
        }
    }

    public void lambdaExpressions() {
        final List<String> items = Arrays.asList("one", "two", "three");

        items.forEach(item -> System.out.println(item));
        items.stream().map(item -> item.toUpperCase()).forEach(System.out::println);

        items.forEach((String item) -> {
            final String processed = item.trim();
            System.out.println(processed);
        });

        final String prefix = "Item: ";
        items.forEach(item -> System.out.println(prefix + item));
    }

    public void tryWithResourcesAndExceptions() {
        final String filename = "test.txt";

        try (FileReader reader = new FileReader(filename);
             BufferedReader buffered = new BufferedReader(reader)) {

            final String line = buffered.readLine();
            final String processed = line != null ? line.trim() : "";

        } catch (IOException e) {
            final String errorMsg = "Error reading file: " + e.getMessage();
            System.err.println(errorMsg);

        }
    }
}
