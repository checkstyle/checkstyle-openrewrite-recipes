package org.checkstyle.autofix.recipe.upperell.after;

public class InputUpperEllTest2 {
    static final long MAX = 9223372036854775807L;

    long calculate(int x) {
        return x * 1000L + 500L;
    }

    void process() {
        long[] values = {10L, 20L, 30L};
        long sum = values[0] + values[1] * 2L;

        if (sum > 100L) {
            System.out.println("Sum: " + (sum - 50L));
        }

        long mixed = 123L + 456L + 789L;
    }
}