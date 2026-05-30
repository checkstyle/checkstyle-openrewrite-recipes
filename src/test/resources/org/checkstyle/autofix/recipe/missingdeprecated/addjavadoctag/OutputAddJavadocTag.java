/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.annotation.MissingDeprecatedCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.missingdeprecated.addjavadoctag;

/**
 * Class Javadoc without deprecated tag.
 * @deprecated
 */
@Deprecated
public class OutputAddJavadocTag {

    /**
    * Field Javadoc without deprecated tag.
    * @deprecated
    */
    @Deprecated
    private int myField;

    /* non-javadoc */
    /**
     * Existing Javadoc without deprecated tag.
     * @deprecated
     */
    @Deprecated
    public void doFoo() {
    }

    /** Single line javadoc. 
     * @deprecated
     */
    @Deprecated
    public void singleLine() {
    }

    /**
     * Multi-line javadoc
     * that ends here
     * @deprecated
     */
    @Deprecated
    public void multiLineEnding() {
    }

    /**
     * Existing Javadoc with deprecated tag.
     *
     * @deprecated
     */
    @SuppressWarnings("deprecation")
    @Deprecated
    public void methodWithAnnotation() {
    }

    /**
     * Weirdly indented javadoc
     * @deprecated
     */
    @Deprecated
    public void weirdlyIndentedMethod() {
    }

    /** Single line field. 
     * @deprecated
     */
    @Deprecated
    public int singleLineField;

    // This is a non-javadoc comment
    /** Single line class. 
     * @deprecated
     */
    @Deprecated
    class SingleLineClass {
    }

    // Line comment is not javadoc
    /**
     * Javadoc after a line comment.
     * @deprecated
     */
    @Deprecated
    public void lineCommentBeforeJavadoc() {
    }

    /* block comment is not javadoc */
    /**
     * Javadoc after a block comment.
     * @deprecated
     */
    @Deprecated
    public void blockCommentBeforeJavadoc() {
    }

    /**
     * Describes the method.
     * Last line has content with no trailing blank line.
     * @deprecated
     */
    @Deprecated
    public void lastLineWithContent() {
    }

    /**
     Continuation line without a leading asterisk.
     * @deprecated
     */
    @Deprecated
    public void indentWithoutAsterisk() {
    }
}
