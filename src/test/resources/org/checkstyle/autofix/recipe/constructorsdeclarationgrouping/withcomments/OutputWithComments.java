/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module
        name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.withcomments;

/**
 * Class where all methods have Javadoc.
 * Javadoc of moved constructors should be moved together.
 */
public class OutputWithComments {

    int x;

    /**
     * Constructor with no parameters.
     */
    OutputWithComments() {}

    /**
     * Constructor with one parameter of type {@code String}.
     * @param s A {@code String}.
     */
    OutputWithComments(String s) {}

    // This method will be removed since issue #12345.
    /**
     * Constructor with one parameter of type {@code int}.
     * @param s An {@code int}.
     */
    OutputWithComments(int x) {}

    /* This method will be removed in a future version. */

    OutputWithComments(String s, int x) {}

    /**
     * A method.
     */
    void foo() {}

    /* Name of the class. */
    private static String NAME = "InputWithComments";
}
