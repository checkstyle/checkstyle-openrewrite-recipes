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

public class InputSimpleEmptyStatement {
    public void suppressedEmptyStatement() {
        if (true)
            ;
    }

    public void columnTest() {
        ; ; // violation 'Empty statement.'
    }

    public void emptyMethod() {
        ; // violation 'Empty statement.'
    }

    public void emptyStatements(boolean cond) {
        for (; cond; )
            ; // violation 'Empty statement.'

        for (; cond; ) {
            ; // violation 'Empty statement.'
        }

        if (true)
            ; // violation 'Empty statement.'

        if (true) {
            ; // violation 'Empty statement.'
        }

        if (cond) {
            int i;
        }
        else {
            ; // violation 'Empty statement.'
        }

        switch (1) {
            case 1:
                ; // violation 'Empty statement.'
            default:
                ; // violation 'Empty statement.'
        }

        while (cond)
            ; // violation 'Empty statement.'

        while (cond) {
            ; // violation 'Empty statement.'
        }

        do
            ; // violation 'Empty statement.'
        while (cond);

        do {
            ; // violation 'Empty statement.'
        }
        while (cond);

        try {
            ; // violation 'Empty statement.'
        }
        catch (Exception ex) {
            ; // violation 'Empty statement.'
        }
        finally {
            ; // violation 'Empty statement.'
        }
    }
}
