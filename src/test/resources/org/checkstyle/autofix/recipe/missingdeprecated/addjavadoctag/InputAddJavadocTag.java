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
 */
@Deprecated // violation 'Must include both @java.lang.Deprecated annotation and @deprecated Javadoc tag with description.'
public class InputAddJavadocTag {

    /**
    * Field Javadoc without deprecated tag.
    */
    @Deprecated // violation 'Must include both @java.lang.Deprecated annotation and @deprecated Javadoc tag with description.'
    private int myField;

    /* non-javadoc */
    /**
     * Existing Javadoc without deprecated tag.
     */
    @Deprecated // violation 'Must include both @java.lang.Deprecated annotation and @deprecated Javadoc tag with description.'
    public void doFoo() {
    }

    /** Single line javadoc. */
    @Deprecated // violation 'Must include both @java.lang.Deprecated annotation and @deprecated Javadoc tag with description.'
    public void singleLine() {
    }

    /**
     * Multi-line javadoc
     * that ends here
     */
    @Deprecated // violation 'Must include both @java.lang.Deprecated annotation and @deprecated Javadoc tag with description.'
    public void multiLineEnding() {
    }

    /**
     * Existing Javadoc with deprecated tag.
     *
     * @deprecated
     */
    @SuppressWarnings("deprecation") // violation 'Must include both @java.lang.Deprecated annotation and @deprecated Javadoc tag with description.'
    public void methodWithAnnotation() {
    }

    /**
     * Weirdly indented javadoc
     */
    @Deprecated // violation 'Must include both @java.lang.Deprecated annotation and @deprecated Javadoc tag with description.'
    public void weirdlyIndentedMethod() {
    }

    /** Single line field. */
    @Deprecated // violation 'Must include both @java.lang.Deprecated annotation and @deprecated Javadoc tag with description.'
    public int singleLineField;

    // This is a non-javadoc comment
    /** Single line class. */
    @Deprecated // violation 'Must include both @java.lang.Deprecated annotation and @deprecated Javadoc tag with description.'
    class SingleLineClass {
    }

    // Line comment is not javadoc
    /**
     * Javadoc after a line comment.
     */
    @Deprecated // violation 'Must include both @java.lang.Deprecated annotation and @deprecated Javadoc tag with description.'
    public void lineCommentBeforeJavadoc() {
    }

    /* block comment is not javadoc */
    /**
     * Javadoc after a block comment.
     */
    @Deprecated // violation 'Must include both @java.lang.Deprecated annotation and @deprecated Javadoc tag with description.'
    public void blockCommentBeforeJavadoc() {
    }

    /**
     * Describes the method.
     * Last line has content with no trailing blank line.*/
    @Deprecated // violation 'Must include both @java.lang.Deprecated annotation and @deprecated Javadoc tag with description.'
    public void lastLineWithContent() {
    }

    /**
     Continuation line without a leading asterisk.
     */
    @Deprecated // violation 'Must include both @java.lang.Deprecated annotation and @deprecated Javadoc tag with description.'
    public void indentWithoutAsterisk() {
    }
}
