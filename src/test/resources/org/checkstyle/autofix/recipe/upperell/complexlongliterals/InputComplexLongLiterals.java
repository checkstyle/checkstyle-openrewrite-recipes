/*xml
<module name="Checker">
  <module name="com.puppycrawl.tools.checkstyle.filters.SuppressionXpathSingleFilter">
    <property name="checks" value="UpperEll"/>
    <property name="query" value="//NUM_LONG[@text='9223372036854775807l' or
    @text='9223372036854775808l']"/>
  </module>
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.UpperEllCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.upperell.complexlongliterals;

public class InputComplexLongLiterals {
    private long withUnderscores = 1_000_000l;            // violation 'Should use uppercase 'L'.'
    private long multipleUnderscores = 1_234_567_890l;    // violation 'Should use uppercase 'L'.'

    private long maxLong = 9223372036854775807l;          // suppressed violation
    private long minLong = -9223372036854775808l;         // suppressed violation

    private Long nullLong = null;
    private Long simpleLong = 1234l;                      // violation 'Should use uppercase 'L'.'
    private Long negativeLong = -5678l;                   // violation 'Should use uppercase 'L'.'
    private Long underscoreLong = 1_000_000l;             // violation 'Should use uppercase 'L'.'
    Long maxLongObject = 9223372036854775807l;            // suppressed violation
    Long minLongObject = -9223372036854775808l;           // suppressed violation

    public long calculate(long input1, long input2) {
        return input1 + input2 + 1000l;                   // violation 'Should use uppercase 'L'.'
    }

    public void lambdaUsage() {
        // violation below, 'Should use uppercase 'L'.'
        java.util.function.LongSupplier supplier = () -> 42l;
        // 3 violations below
        java.util.Arrays.asList(1l, 2l, 3l).forEach(System.out::println);
    }

    public java.util.List<Long> getNumbers() {
        return java.util.Arrays.asList(100l, 200l, 300l);   // 3 violations
    }

    public void longObjectOperations() {
        Long a = null;
        Long b = 1234l;                                   // violation 'Should use uppercase 'L'.'
        Long c = Long.valueOf(5678l);                   // violation 'Should use uppercase 'L'.'
        Long d = Long.valueOf(9999l);                   // violation 'Should use uppercase 'L'.'

        Long conditional = (a != null) ? a : 0l;          // violation 'Should use uppercase 'L'.'
        long primitive = b != null ? b : 0l;              // violation 'Should use uppercase 'L'.'
        Long boxed = primitive + 1000l;                   // violation 'Should use uppercase 'L'.'
    }

    public Long methodReturningLong(Long param) {
        if (param == null) {
            return 12345l;                                // violation 'Should use uppercase 'L'.'
        }
        return param + 6789l;                             // violation 'Should use uppercase 'L'.'
    }
}
