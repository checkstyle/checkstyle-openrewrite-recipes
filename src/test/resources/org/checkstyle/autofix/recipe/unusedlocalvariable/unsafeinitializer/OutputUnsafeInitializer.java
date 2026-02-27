/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.unsafeinitializer;

public class OutputUnsafeInitializer {

    public void method() {
        sideEffect();
    }

    public int sideEffect() {
        System.out.println("Side effect executed!");
        return 42;
    }

    public void anotherMethod() {
        int a = 1, c = 2;
        sideEffect();
        System.out.println(a + c);
    }

}
