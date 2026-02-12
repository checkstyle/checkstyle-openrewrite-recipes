/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.design.FinalClassCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.finalclass.modifierorder;

public class InputModifierOrder { // violation 'Class InputModifierOrder should be declared as final.'
    private InputModifierOrder() {
    }
}
