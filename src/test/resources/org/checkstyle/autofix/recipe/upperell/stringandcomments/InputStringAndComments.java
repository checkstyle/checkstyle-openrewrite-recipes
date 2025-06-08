package org.checkstyle.autofix.recipe.upperell.stringandcomments;

public class InputStringAndComments {
    /**
     * This comment mentions 123l but should not change
     */
    private String message = "The value 456l should not change in strings";
    private String code = "long value = 789l;"; // This 789l in string should not change

    // Only this actual long literal should change
    private long actualLong = 999l;

    /*
     * Multi-line comment with 111l should not change
     */
    public void method() {
        // Single line comment with 222l should not change
        long value = 333l; // This should change
    }
}
