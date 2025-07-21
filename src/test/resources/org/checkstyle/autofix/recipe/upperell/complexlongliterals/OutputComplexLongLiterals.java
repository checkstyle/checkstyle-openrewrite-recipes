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

public class OutputComplexLongLiterals {
    private long withUnderscores = 1_000_000L;
    private long multipleUnderscores = 1_234_567_890L;

    private long maxLong = 9223372036854775807l;    //suppressed violation
    private long minLong = -9223372036854775808l;    //suppressed violation

    private Long nullLong = null;
    private Long simpleLong = 1234L;
    private Long negativeLong = -5678L;
    private Long underscoreLong = 1_000_000L;
    Long maxLongObject = 9223372036854775807l;    //suppressed violation
    Long minLongObject = -9223372036854775808l;    //suppressed violation

    public long calculate(long input1, long input2) {
        return input1 + input2 + 1000L;
    }

    public void lambdaUsage() {
        java.util.function.LongSupplier supplier = () -> 42L;
        java.util.Arrays.asList(1L, 2L, 3L).forEach(System.out::println);
    }

    public java.util.List<Long> getNumbers() {
        return java.util.Arrays.asList(100L, 200L, 300L);
    }

    public void longObjectOperations() {
        Long a = null;
        Long b = 1234L;
        Long c = Long.valueOf(5678L);
        Long d = new Long(9999L);

        Long conditional = (a != null) ? a : 0L;
        long primitive = b != null ? b : 0L;
        Long boxed = primitive + 1000L;
    }

    public Long methodReturningLong(Long param) {
        if (param == null) {
            return 12345L;
        }
        return param + 6789L;
    }
}
