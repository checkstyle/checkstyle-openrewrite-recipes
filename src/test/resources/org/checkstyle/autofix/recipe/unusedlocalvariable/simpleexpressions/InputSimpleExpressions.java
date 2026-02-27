/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.unusedlocalvariable.simpleexpressions;

public class InputSimpleExpressions {

    public void method() {
        int used = 1;
        int unusedBinary = used + 2; // violation 'Unused named local variable'
        int unusedUnary = -used; // violation 'Unused named local variable'
        int unusedParen = (used + 1); // violation 'Unused named local variable'
        int unusedIdCopy = used; // violation 'Unused named local variable'
        String unusedCast = (String) null; // violation 'Unused named local variable'
        System.out.println(used);
    }
}
