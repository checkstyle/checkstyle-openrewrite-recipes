/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.imports.AvoidStarImportCheck">
    </module>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.avoidstarimport.killmutation2supportclass;

public class OutputKillMutation2SupportClass {
    public static final int STATIC_CONSTANT = 42;
    public int instanceField = 10;
}
