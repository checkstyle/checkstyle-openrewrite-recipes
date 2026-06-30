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
 * All constructors and methods have Javadoc. Verifies that Javadoc of moved
 * constructors are moved together.
 */
public class InputWithComments {

    int x;

    /**
     * Constructor with no parameters.
     */
    InputWithComments() {}

    /**
     * Constructor with one parameter of type {@code String}.
     * @param s A {@code String}.
     */
    InputWithComments(String s) {}

    /**
     * A method.
     */
    void foo() {}

    /* Name of the class. */
    private static String NAME = "InputWithComments";

    // This method will be removed since issue #12345.
    /**
     * Constructor with one parameter of type {@code int}.
     * @param s An {@code int}.
     */
    InputWithComments(int x) {}
    // violation above 'Constructors should be grouped together.*'

    /* This method will be removed in a future version. */

    InputWithComments(String s, int x) {}
    // violation above 'Constructors should be grouped together.*'
}
