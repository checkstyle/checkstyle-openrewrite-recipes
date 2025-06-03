package org.checkstyle.autofix.recipe.upperell.before;

public class InputUpperEllTest2 {
    static final long MAX = 9223372036854775807l;

    long calculate(int x) {
        return x * 1000l + 500l;
    }

    void process() {
        long[] values = {10l, 20l, 30l};
        long sum = values[0] + values[1] * 2l;

        if (sum > 100l) {
            System.out.println("Sum: " + (sum - 50l));
        }

        long mixed = 123l + 456L + 789l;
    }
}