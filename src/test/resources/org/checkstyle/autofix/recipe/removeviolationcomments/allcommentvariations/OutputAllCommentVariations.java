package org.checkstyle.autofix.recipe.removeviolationcomments.allcommentvariations;

public class OutputAllCommentVariations {

    private int field1;
    private int field2;
    private int field3;
    private int field4;
    private int field5;
    private int field6;

    /* This violation should not be removed - multiline */
    private int field7; // this is not a violation
    private int field8; // some regular comment

    // Another regular comment
    private int field9;
    private int field10; // random comment
    private int field11;
    private int field12;

    /**
     * Javadoc with violation word should stay
     */
    public void method() {
        int local;
        int other; // normal comment
    }
}