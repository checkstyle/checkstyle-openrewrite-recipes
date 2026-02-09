/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.EmptyStatementCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.emptystatement.simpleemptystatement;

public class InputSimpleEmptyStatement {
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
