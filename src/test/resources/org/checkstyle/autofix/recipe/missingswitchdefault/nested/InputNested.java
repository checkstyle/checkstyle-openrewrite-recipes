/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.MissingSwitchDefaultCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.missingswitchdefault.nested;

public class InputNested {
    void foo(int x, int y) {
        switch (x) { // violation 'switch without "default" clause.'
            case 1:
                switch (y) { // violation 'switch without "default" clause.'
                    case 2:
                        break;
                }
                break;
        }
    }
}
