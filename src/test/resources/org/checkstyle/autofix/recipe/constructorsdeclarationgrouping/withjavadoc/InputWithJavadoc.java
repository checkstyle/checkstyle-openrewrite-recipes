/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module
        name="com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.constructorsdeclarationgrouping.withjavadoc;

/**
 * All constructors and methods have Javadoc. Verifies that Javadoc of moved
 * constructors are moved together.
 */
public class InputWithJavadoc {

    int x;

    /**
     * Constructor with no parameters.
     */
    InputWithJavadoc() {}

    /**
     * Constructor with one parameter of type {@code String}.
     * @param s A {@code String}.
     */
    InputWithJavadoc(String s) {}

    /**
     * A method.
     */
    void foo() {}

    /**
     * Constructor with one parameter of type {@code int}.
     * @param s An {@code int}.
     */
    InputWithJavadoc(int x) {}
    // violation above 'Constructors should be grouped together.*'

    /**
     * Constructor with two parameters of types {@code String} and {@code int}.
     * @param s A {@code String}.
     * @param x An {@code int}.
     */
    InputWithJavadoc(String s, int x) {}
    // violation above 'Constructors should be grouped together.*'
}
