package org.checkstyle.autofix.recipe.upperell.complexlongliterals;

public class InputComplexLongLiterals {
    private long withUnderscores = 1_000_000l;
    private long multipleUnderscores = 1_234_567_890l;

    private long maxLong = 9223372036854775807l;
    private long minLong = -9223372036854775808l;

    private Long nullLong = null;
    private Long simpleLong = 1234l;
    private Long negativeLong = -5678l;
    private Long underscoreLong = 1_000_000l;
    Long maxLongObject = 9223372036854775807l;
    Long minLongObject = -9223372036854775808l;

    public long calculate(long input1, long input2) {
        return input1 + input2 + 1000l;
    }

    public void lambdaUsage() {
        java.util.function.LongSupplier supplier = () -> 42l;
        java.util.Arrays.asList(1l, 2l, 3l).forEach(System.out::println);
    }

    public java.util.List<Long> getNumbers() {
        return java.util.Arrays.asList(100l, 200l, 300l);
    }

    public void longObjectOperations() {
        Long a = null;
        Long b = 1234l;
        Long c = Long.valueOf(5678l);
        Long d = new Long(9999l);

        Long conditional = (a != null) ? a : 0l;
        long primitive = b != null ? b : 0l;
        Long boxed = primitive + 1000l;
    }

    public Long methodReturningLong(Long param) {
        if (param == null) {
            return 12345l;
        }
        return param + 6789l;
    }
}
