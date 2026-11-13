/*xml
<module name="Checker">
  <module name="SuppressionCommentFilter">
    <property name="offCommentFormat" value="CHECKSTYLE:OFF"/>
    <property name="onCommentFormat" value="CHECKSTYLE:ON"/>
  </module>
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocLeadingAsteriskAlignCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.javadocleadingasteriskalign.selectivealignment;

public class OutputSelectiveAlignment {

    /**
     * First line
     * Misaligned line
     */
    public void firstMethod() {
    }

    // CHECKSTYLE:OFF
    /**
      * This Javadoc is intentionally misaligned but suppressed.
     */
    public void secondMethod() {
    }
    // CHECKSTYLE:ON
}
