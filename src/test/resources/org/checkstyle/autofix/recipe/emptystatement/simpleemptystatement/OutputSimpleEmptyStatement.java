/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.EmptyStatementCheck"/>
    <module name="SuppressionXpathSingleFilter">
      <property name="checks" value="EmptyStatementCheck"/>
      <property name="query" value="//METHOD_DEF[./IDENT[@text='suppressedEmptyStatement']]//EMPTY_STAT"/>
    </module>
    <module name="SuppressionXpathSingleFilter">
      <property name="checks" value="EmptyStatementCheck"/>
      <property name="query" value="//CLASS_DEF[./IDENT[@text='InputSimpleEmptyStatement']]//METHOD_DEF[./IDENT[@text='columnTest']]//SLIST/EMPTY_STAT[2]"/>
    </module>
    <module name="SuppressionXpathSingleFilter">
      <property name="checks" value="EmptyStatementCheck"/>
      <property name="query" value="//CLASS_DEF[./IDENT[@text='OutputSimpleEmptyStatement']]//METHOD_DEF[./IDENT[@text='columnTest']]//SLIST/EMPTY_STAT"/>
    </module>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.emptystatement.simpleemptystatement;

public class OutputSimpleEmptyStatement {
    public void suppressedEmptyStatement() {
        if (true)
            ;
    }

    public void columnTest() { ;
    }

    public void emptyMethod() {
    }

    public void emptyStatements(boolean cond) {
        for (; cond; ) {
        }

        for (; cond; ) {
        }

        if (true) {
        }

        if (true) {
        }

        if (cond) {
            int i;
        }
        else {
        }

        switch (1) {
            case 1:
            default:
        }

        while (cond) {
        }

        while (cond) {
        }

        do {
        }
        while (cond);

        do {
        }
        while (cond);

        try {
        }
        catch (Exception ex) {
        }
        finally {
        }
    }
}
