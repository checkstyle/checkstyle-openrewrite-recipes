/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.unsafeinitializer;

public class InputUnsafeInitializer {

    public void method() {
        int unused = sideEffect(); // violation 'Unused named local variable'
    }

    public int sideEffect() {
        System.out.println("Side effect executed!");
        return 42;
    }

    public void anotherMethod() {
        int a = 1, b = sideEffect(), c = 2; // violation 'Unused named local variable'
        System.out.println(a + c);
    }

}
