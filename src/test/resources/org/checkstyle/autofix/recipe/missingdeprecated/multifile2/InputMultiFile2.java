/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.annotation.MissingDeprecatedCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.missingdeprecated.multifile2;

public class InputMultiFile2 {
    /**
     * Existing Javadoc without deprecated tag.
     */
    public void hasNoViolationOnLine15() {}
}
