/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.EmptyStatementCheck"/>
    <module name="SuppressionXpathSingleFilter">
      <property name="checks" value="EmptyStatementCheck"/>
      <property name="query" value="//CLASS_DEF[./IDENT[@text='InputMultiFileEmptyStatement2'] or ./IDENT[@text='OutputMultiFileEmptyStatement2']]//EMPTY_STAT"/>
    </module>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.emptystatement.multifileemptystatement1;

public class OutputMultiFileEmptyStatement1 {
    public void method() {
    }
}
