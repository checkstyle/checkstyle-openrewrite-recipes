/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocLeadingAsteriskAlignCheck">
      <property name="tabWidth" value="4"/>
    </module>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.javadocleadingasteriskalign.tabwidth;

public class InputTabWidth {

    public static class Inner {
        // violation 9 lines below 'Leading asterisk has incorrect indentation level 2, expected is 1.'
        /**
         * Aligned javadoc comment.
         */
        /**
         * Line 1
		 * Line 2
	  	 * Line 2b
         text without asterisk *
          * Line 3
        */ // violation 'Leading asterisk has incorrect indentation level 0, expected is 1.'
        public void test() {
        }
    }

    /*
      * Misaligned block comment
    */
    public void other() {
    }
}
