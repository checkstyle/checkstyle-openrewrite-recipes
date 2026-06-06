/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.AvoidNoArgumentSuperConstructorCallCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.avoidnoargumentsuperconstructorcall.nestedsupercall;

class InputNestedSuperCall {
    void test() {
        consume(() -> {
            class Local {
                Local() {
                    super(); // violation 'Unnecessary call to superclass constructor with no arguments.'
                }
            }
        });
    }
    void consume(Runnable r) {}
}
