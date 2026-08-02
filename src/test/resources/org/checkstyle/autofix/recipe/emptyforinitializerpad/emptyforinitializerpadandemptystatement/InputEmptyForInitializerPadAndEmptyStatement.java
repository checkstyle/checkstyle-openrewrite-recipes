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

public class InputEmptyForInitializerPadAndEmptyStatement {
    public void test() {
        for ( ; ; ) { // violation "';' is preceded with whitespace."
            break;
        }
        int x = 1; ; // violation "Empty statement."
    }
}
