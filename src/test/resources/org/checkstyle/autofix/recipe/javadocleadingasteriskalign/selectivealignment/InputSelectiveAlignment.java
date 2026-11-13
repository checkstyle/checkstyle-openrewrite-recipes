
/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuppressionCommentFilter">
      <property name="offCommentFormat" value="CHECKSTYLE:OFF"/>
      <property name="onCommentFormat" value="CHECKSTYLE:ON"/>
    </module>
    <module name="com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocLeadingAsteriskAlignCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.javadocleadingasteriskalign.selectivealignment;

public class InputSelectiveAlignment {

    // violation 3 lines below 'Leading asterisk has incorrect indentation level 2, expected is 1.'
    /**
     * First line
      * Misaligned line
    */ // violation 'Leading asterisk has incorrect indentation level 0, expected is 1.'
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
