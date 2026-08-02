/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.whitespace.EmptyForInitializerPadCheck">
      <property name="option" value="nospace"/>
    </module>
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.EmptyStatementCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.emptyforinitializerpad.emptyforinitializerpadandemptystatement;

public class OutputEmptyForInitializerPadAndEmptyStatement {
    public void test() {
        for (; ; ) {
            break;
        }
        int x = 1;
    }
}
