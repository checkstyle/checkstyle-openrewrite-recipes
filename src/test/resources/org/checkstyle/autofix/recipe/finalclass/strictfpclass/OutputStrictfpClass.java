/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.design.FinalClassCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.finalclass.strictfpclass;

final strictfp class OutputStrictfpClass {
    private OutputStrictfpClass() {
    }
}
