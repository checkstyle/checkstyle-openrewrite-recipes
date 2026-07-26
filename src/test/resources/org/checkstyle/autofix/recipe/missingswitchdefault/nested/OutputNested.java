/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.MissingSwitchDefaultCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.missingswitchdefault.nested;

public class OutputNested {
    void foo(int x, int y) {
        switch (x) {
            case 1:
                switch (y) {
                    case 2:
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }
}
