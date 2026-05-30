/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.annotation.MissingDeprecatedCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.missingdeprecated.multifile1;

public class OutputMultiFile1 {
    /**
     * @deprecated
     */
    @Deprecated
    public void hasViolationOnLine15() {}
}
