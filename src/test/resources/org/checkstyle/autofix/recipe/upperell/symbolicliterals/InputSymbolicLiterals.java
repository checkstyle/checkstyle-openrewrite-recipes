/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.UpperEllCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.upperell.symbolicliterals;

public class InputSymbolicLiterals {
    private long minLong = -9223372036854775808l;    // violation 'Should use uppercase 'L'.'
    private Long negativeLong = -5678l;              // violation 'Should use uppercase 'L'.'
    long a = -0xAl;                                  // violation 'Should use uppercase 'L'.'
    long b = +-12l;                                  // violation 'Should use uppercase 'L'.'
    long c = +-(-(-(-4l)));;                         // violation 'Should use uppercase 'L'.'
}
