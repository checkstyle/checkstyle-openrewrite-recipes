/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.EmptyStatementCheck"/>
    <module name="SuppressionXpathSingleFilter">
      <property name="checks" value="EmptyStatementCheck"/>
      <property name="query" value="//EMPTY_STAT"/>
    </module>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.emptystatement.noviolationemptystatement;

public class InputNoViolationEmptyStatement {
    public void emptyMethod() {
        ;
    }
}
