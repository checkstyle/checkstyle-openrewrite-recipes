/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.UpperEllCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.upperell.symbolicliterals;

public class InputSymbolicLiterals {
    private long minLong = -9223372036854775808l;
    private Long negativeLong = -5678l;
    long a = -0xAl;
    long b = +-12l;
    long c = +-(-(-(-4l)));;
}
