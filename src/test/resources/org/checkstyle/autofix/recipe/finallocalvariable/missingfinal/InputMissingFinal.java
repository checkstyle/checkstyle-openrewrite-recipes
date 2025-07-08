package org.checkstyle.autofix.recipe.finallocalvariable.missingfinal;

public class InputMissingFinal {
    public void computeSum() {
        int a = 5;
        int b = 10;
        int sum = a + b;

        System.out.println("Sum: " + sum);
    }
}
