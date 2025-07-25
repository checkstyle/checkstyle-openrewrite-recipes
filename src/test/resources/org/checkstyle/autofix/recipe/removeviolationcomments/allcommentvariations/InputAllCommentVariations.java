package org.checkstyle.autofix.recipe.removeviolationcomments.allcommentvariations;

public class InputAllCommentVariations {

    private int field1; // violation
    private int field2; // 2 violation message
    private int field3; //   violation
    private int field4; //  3  violation text
    private int field5; // violation - missing javadoc
    private int field6; // 10 violation some descriptive text

    /* This violation should not be removed - multiline */
    private int field7; // this is not a violation
    private int field8; // some regular comment

    // Another regular comment
    private int field9; // violation
    // violation below
    private int field10; // random comment
    private int field11; // 25 violation
    private int field12;
    // violation above

    /**
     * Javadoc with violation word should stay
     */
    public void method() {
        int local; // violation
        // violation in standalone comment
        int other; // normal comment
    }
}