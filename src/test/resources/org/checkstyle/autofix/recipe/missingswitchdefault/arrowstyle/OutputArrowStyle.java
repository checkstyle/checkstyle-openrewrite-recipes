/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.MissingSwitchDefaultCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.missingswitchdefault.arrowstyle;

public class OutputArrowStyle {
    void foo(int x) {
        switch (x) {
            case 1 -> System.out.println(1);
            default -> {
            }
        }
    }
}
