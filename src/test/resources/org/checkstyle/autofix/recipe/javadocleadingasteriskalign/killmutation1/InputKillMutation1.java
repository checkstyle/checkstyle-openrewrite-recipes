/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocLeadingAsteriskAlignCheck"/>
    <module name="com.puppycrawl.tools.checkstyle.filters.SuppressionSingleFilter">
      <property name="checks" value="JavadocLeadingAsteriskAlignCheck"/>
      <property name="files" value=".*KillMutation2\.java"/>
    </module>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.javadocleadingasteriskalign.killmutation1;

public class InputKillMutation1 {
    // violation 5 lines below 'Leading asterisk has incorrect indentation level 2, expected is 1.'
    // violation 5 lines below 'Leading asterisk has incorrect indentation level 0, expected is 1.'
    /**
     * Line 1

      * Line 2
    */
    public void test() {
    }
}
