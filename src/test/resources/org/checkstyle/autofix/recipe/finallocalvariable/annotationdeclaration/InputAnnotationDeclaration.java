/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.FinalLocalVariableCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.finallocalvariable.annotationdeclaration;

import javax.annotation.Nonnull;

public class InputAnnotationDeclaration {
    public void basicLocalVariables() {
        @Nonnull String name = "John Doe";         // violation, "should be declared final"

        @Nonnull
        @Deprecated String gender = "Male";         // violation, "should be declared final"

        @Nonnull @Deprecated
        String text = "Hello";         // violation, "should be declared final"
    }
}
