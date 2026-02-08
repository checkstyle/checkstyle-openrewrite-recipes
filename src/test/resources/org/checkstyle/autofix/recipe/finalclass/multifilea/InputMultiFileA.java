/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.design.FinalClassCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.finalclass.multifilea;

class InputMultiFileA { // violation 'Class InputMultiFileA should be declared as final.'
    private InputMultiFileA() {}
}
