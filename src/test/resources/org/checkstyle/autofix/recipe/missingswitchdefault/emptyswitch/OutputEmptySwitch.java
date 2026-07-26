/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.MissingSwitchDefaultCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.missingswitchdefault.emptyswitch;

public class OutputEmptySwitch {
    void foo(int x) {
        switch (x) {
            default:
                break;
        }
    }
}
