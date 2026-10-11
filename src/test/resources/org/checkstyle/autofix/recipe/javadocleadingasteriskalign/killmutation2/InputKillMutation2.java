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

package org.checkstyle.autofix.recipe.javadocleadingasteriskalign.killmutation2;

public class InputKillMutation2 {
    /**
     * Line 1
      * Line 2
    */
    public void test() {
    }
}
