/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.EmptyStatementCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.emptystatement.simpleemptystatement;

public class OutputSimpleEmptyStatement {
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
