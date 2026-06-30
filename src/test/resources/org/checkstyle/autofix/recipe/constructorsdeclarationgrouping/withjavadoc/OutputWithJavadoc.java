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
public class OutputWithJavadoc {

    int x;

    /**
     * Constructor with no parameters.
     */
    OutputWithJavadoc() {}

    /**
     * Constructor with one parameter of type {@code String}.
     * @param s A {@code String}.
     */
    OutputWithJavadoc(String s) {}

    /**
     * Constructor with one parameter of type {@code int}.
     * @param s An {@code int}.
     */
    OutputWithJavadoc(int x) {}

    /**
     * Constructor with two parameters of types {@code String} and {@code int}.
     * @param s A {@code String}.
     * @param x An {@code int}.
     */
    OutputWithJavadoc(String s, int x) {}

    /**
     * A method.
     */
    void foo() {}
}
