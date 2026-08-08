/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.EmptyStatementCheck"/>
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.IllegalTokenCheck">
      <property name="tokens" value="EMPTY_STAT"/>
    </module>
    <module name="com.puppycrawl.tools.checkstyle.filters.SuppressionSingleFilter">
      <property name="checks" value="EmptyStatementCheck"/>
      <property name="lines" value="27"/>
    </module>
    <module name="com.puppycrawl.tools.checkstyle.filters.SuppressionSingleFilter">
      <property name="checks" value="IllegalToken"/>
      <property name="lines" value="28"/>
    </module>
    <module name="com.puppycrawl.tools.checkstyle.filters.SuppressionSingleFilter">
      <property name="checks" value="IllegalToken"/>
      <property name="files" value=".*OutputIllegalToken\.java"/>
    </module>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.emptystatement.illegaltoken;

public class InputIllegalToken {
    public void test() {
        ; // violation
        ; // violation
    }
}
