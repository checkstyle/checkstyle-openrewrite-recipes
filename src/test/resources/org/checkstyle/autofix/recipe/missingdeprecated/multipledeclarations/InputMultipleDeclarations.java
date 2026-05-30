/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.annotation.MissingDeprecatedCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.missingdeprecated.multipledeclarations;

/**
 * @deprecated Use something else.
 */
public class InputMultipleDeclarations { // violation 'Must include both @java.lang.Deprecated annotation and @deprecated Javadoc tag with description.'

    /**
     * @deprecated
     */
    private int fieldWithoutAnnotation; // violation 'Must include both @java.lang.Deprecated annotation and @deprecated Javadoc tag with description.'

    /**
     * @deprecated
     */
    int packagePrivateField; // violation 'Must include both @java.lang.Deprecated annotation and @deprecated Javadoc tag with description.'

    /**
     * @deprecated
     */
    public InputMultipleDeclarations() {} // violation 'Must include both @java.lang.Deprecated annotation and @deprecated Javadoc tag with description.'

    /**
     * @deprecated
     */
    InputMultipleDeclarations(int x) {} // violation 'Must include both @java.lang.Deprecated annotation and @deprecated Javadoc tag with description.'

    /**
     * @deprecated
     */
    void packagePrivateMethod() {} // violation 'Must include both @java.lang.Deprecated annotation and @deprecated Javadoc tag with description.'

    /**
     * @deprecated
     */
    class NestedClass {} // violation 'Must include both @java.lang.Deprecated annotation and @deprecated Javadoc tag with description.'

    /**
     * Existing Javadoc without deprecated tag.
     * But it has no @Deprecated annotation either, so Checkstyle ignores it? Wait, Checkstyle doesn't check it if neither is present.
     */
    public void noViolationMethod() {}

    /**
     * Existing Javadoc without deprecated tag.
     */
    public int noViolationField;

    /**
     * @deprecated
     */
    public void methodWithLocalClass() { // violation 'Must include both @java.lang.Deprecated annotation and @deprecated Javadoc tag with description.'
        /**
         * @deprecated
         */
        class LocalClass {} // violation 'Must include both @java.lang.Deprecated annotation and @deprecated Javadoc tag with description.'
    }

    /**
     * @deprecated
     */
    Runnable r = new Runnable() { // violation 'Must include both @java.lang.Deprecated annotation and @deprecated Javadoc tag with description.'
        /**
         * @deprecated
         */
        public void run() {} // violation 'Must include both @java.lang.Deprecated annotation and @deprecated Javadoc tag with description.'
    };
}
