/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.MissingSwitchDefaultCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.missingswitchdefault.emptycase;

public class InputEmptyCase {
    void foo(int x) {
        switch (x) { // violation 'switch without "default" clause.'
            case 1:
            case 2:
        }
    }
}
