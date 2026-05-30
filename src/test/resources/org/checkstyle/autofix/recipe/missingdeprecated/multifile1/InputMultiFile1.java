/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.annotation.MissingDeprecatedCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.missingdeprecated.multifile1;

public class InputMultiFile1 {
    /**
     * @deprecated
     */
    public void hasViolationOnLine15() {} // violation 'Must include both @java.lang.Deprecated annotation and @deprecated Javadoc tag with description.'
}
