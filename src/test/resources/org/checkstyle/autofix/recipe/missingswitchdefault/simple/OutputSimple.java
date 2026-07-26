/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.MissingSwitchDefaultCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.missingswitchdefault.simple;

public class OutputSimple {
    void foo(int x) {
        switch (x) {
            case 1:
                break;
            default:
                break;
        }
    }
}
