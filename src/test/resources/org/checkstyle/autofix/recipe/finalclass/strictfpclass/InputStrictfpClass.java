/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.design.FinalClassCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.finalclass.strictfpclass;

strictfp class InputStrictfpClass { // violation 'Class InputStrictfpClass should be declared as final.'
    private InputStrictfpClass() {
    }
}
