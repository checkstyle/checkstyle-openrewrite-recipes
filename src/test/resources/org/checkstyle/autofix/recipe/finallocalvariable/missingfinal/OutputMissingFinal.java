package org.checkstyle.autofix.recipe.finallocalvariable.missingfinal;

public class OutputMissingFinal {
    public void computeSum() {
        final int a = 5;
        final int b = 10;
        final int sum = a + b;

        System.out.println("Sum: " + sum);
    }
}
