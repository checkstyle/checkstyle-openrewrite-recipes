/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.MissingSwitchDefaultCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.missingswitchdefault.emptycase;

public class OutputEmptyCase {
    void foo(int x) {
        switch (x) {
            case 1:
            case 2:
                break;
            default:
                break;
        }
    }
}
