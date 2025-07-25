/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.UpperEllCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.upperell.stringandcomments;

public class InputStringAndComments {
    /**
     * This comment mentions 123l but should not change
     */
    private String message = "The value 456l should not change in strings";
    private String code = "long value = 789l;"; // This 789l in string should not change

    private long actualLong = 999l;    // violation 'Should use uppercase 'L'.'

    /*
     * Multi-line comment with 111l should not change
     */
    public void method() {
        // Single line comment with 222l should not change
        long value = 333l;    // violation 'Should use uppercase 'L'.'
    }
}
