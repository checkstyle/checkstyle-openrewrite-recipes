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

public class OutputTabWidth {

    public static class Inner {
        /**
         * Aligned javadoc comment.
         */
        /**
         * Line 1
		 * Line 2
	  	 * Line 2b
         text without asterisk *
         * Line 3
         */
        public void test() {
        }
    }

    /*
      * Misaligned block comment
    */
    public void other() {
    }
}
