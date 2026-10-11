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

public class OutputKillMutation1 {
    /**
     * Line 1

     * Line 2
     */
    public void test() {
    }
}
