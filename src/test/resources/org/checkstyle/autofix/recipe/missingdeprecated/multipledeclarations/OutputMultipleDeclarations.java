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
@Deprecated
public class OutputMultipleDeclarations {

    /**
     * @deprecated
     */
    @Deprecated
    private int fieldWithoutAnnotation;

    /**
     * @deprecated
     */
    @Deprecated
    int packagePrivateField;

    /**
     * @deprecated
     */
    @Deprecated
    public OutputMultipleDeclarations() {}

    /**
     * @deprecated
     */
    @Deprecated
    OutputMultipleDeclarations(int x) {}

    /**
     * @deprecated
     */
    @Deprecated
    void packagePrivateMethod() {}

    /**
     * @deprecated
     */
    @Deprecated
    class NestedClass {}

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
    @Deprecated
    public void methodWithLocalClass() {
        /**
         * @deprecated
         */
        @Deprecated
        class LocalClass {}
    }

    /**
     * @deprecated
     */
    @Deprecated
    Runnable r = new Runnable() {
        /**
         * @deprecated
         */
        @Deprecated
        public void run() {}
    };
}
