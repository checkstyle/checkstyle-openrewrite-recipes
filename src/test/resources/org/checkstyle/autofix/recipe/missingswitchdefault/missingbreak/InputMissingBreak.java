/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.MissingSwitchDefaultCheck"/>
  </module>
</module>
*/
package org.checkstyle.autofix.recipe.missingswitchdefault.missingbreak;

public class InputMissingBreak {
    void foo(int x) {
        switch (x) { // violation 'switch without "default" clause.'
            case 1:
                System.out.println(x);
        }
    }
}
