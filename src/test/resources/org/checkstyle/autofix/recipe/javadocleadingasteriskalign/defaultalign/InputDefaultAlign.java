/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocLeadingAsteriskAlignCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.javadocleadingasteriskalign.defaultalign;

public class InputDefaultAlign {
    // violation 3 lines below 'Leading asterisk has incorrect indentation level 7, expected is 6.'
    /**
     * Line 1
      * Line 2
    */ // violation 'Leading asterisk has incorrect indentation level 5, expected is 6.'
    public void test() {
    }
}
