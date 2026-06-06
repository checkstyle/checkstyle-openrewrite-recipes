/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.AvoidNoArgumentSuperConstructorCallCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.avoidnoargumentsuperconstructorcall.nestedsupercall;

class OutputNestedSuperCall {
    void test() {
        consume(() -> {
            class Local {
                Local() {
                }
            }
        });
    }
    void consume(Runnable r) {}
}
