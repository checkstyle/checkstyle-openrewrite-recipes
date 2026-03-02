/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UseEnhancedSwitchCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.useenhancedswitch.mutationtests19;

public class InputMutationTests19 {
    void oneLiner(int x) {
        // violation below, 'Switch can be replaced with enhanced switch'
        switch (x) { case 1:        {
            System.out.println("one");
            System.out.println("two");
            break;
        } default: break; }
    }
}
