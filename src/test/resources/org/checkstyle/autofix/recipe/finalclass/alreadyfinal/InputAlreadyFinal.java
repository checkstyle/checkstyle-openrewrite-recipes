/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.design.FinalClassCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.finalclass.alreadyfinal;

public final class InputAlreadyFinal {
    private InputAlreadyFinal() {
    }
}
