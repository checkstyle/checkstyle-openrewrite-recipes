package org.checkstyle.autofix.recipe.javadocleadingasteriskalign.tabwidthinheritance;

public class InputTabWidthInheritance {

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
