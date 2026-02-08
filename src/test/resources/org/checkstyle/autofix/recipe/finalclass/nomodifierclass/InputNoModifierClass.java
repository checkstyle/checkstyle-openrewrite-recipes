/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.design.FinalClassCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.finalclass.nomodifierclass;

class InputNoModifierClass { // violation 'Class InputNoModifierClass should be declared as final.'
    private InputNoModifierClass() {
    }
}
