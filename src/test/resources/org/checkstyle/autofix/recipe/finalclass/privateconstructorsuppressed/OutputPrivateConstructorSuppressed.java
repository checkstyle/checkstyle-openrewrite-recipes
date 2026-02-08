/*xml
<module name="Checker">
  <module name="SuppressWarningsFilter"/>
  <module name="TreeWalker">
    <module name="SuppressWarningsHolder"/>
    <module name="com.puppycrawl.tools.checkstyle.checks.design.FinalClassCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.finalclass.privateconstructorsuppressed;

@SuppressWarnings("checkstyle:FinalClass")
public class OutputPrivateConstructorSuppressed {
    private OutputPrivateConstructorSuppressed() {
    }
}
