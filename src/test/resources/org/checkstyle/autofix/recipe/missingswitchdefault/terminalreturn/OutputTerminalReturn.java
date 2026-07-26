/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.MissingSwitchDefaultCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.missingswitchdefault.terminalreturn;

public class OutputTerminalReturn {
    int foo(int x) {
        switch (x) {
            case 1:
                return 1;
            default:
                break;
        }
        return 0;
    }
}
