/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.MissingSwitchDefaultCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.missingswitchdefault.emptyswitch;

public class InputEmptySwitch {
    void foo(int x) {
        switch (x) { // violation 'switch without "default" clause.'
        }
    }
}
