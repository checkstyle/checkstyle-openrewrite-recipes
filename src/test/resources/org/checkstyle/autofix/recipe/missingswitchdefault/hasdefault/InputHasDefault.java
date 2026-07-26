/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.MissingSwitchDefaultCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.missingswitchdefault.hasdefault;

public class InputHasDefault {
    void foo(int x) {
        switch (x) {
            case 1:
                break;
            default:
                break;
        }
    }
}
