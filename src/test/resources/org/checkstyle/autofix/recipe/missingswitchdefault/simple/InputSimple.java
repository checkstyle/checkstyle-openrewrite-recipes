/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.MissingSwitchDefaultCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.missingswitchdefault.simple;

public class InputSimple {
    void foo(int x) {
        switch (x) { // violation 'switch without "default" clause.'
            case 1:
                break;
        }
    }
}
