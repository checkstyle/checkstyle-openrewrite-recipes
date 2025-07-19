package org.checkstyle.autofix.recipe.upperell.symbolicliterals;

public class OutputSymbolicLiterals {
    private long minLong = -9223372036854775808L;
    private Long negativeLong = -5678L;
    long a = -0xAL;
    long b = +-12L;
    long c = +-(-(-(-4L)));;
}
