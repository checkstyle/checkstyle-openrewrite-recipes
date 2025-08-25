/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.FinalLocalVariableCheck"/>
  </module>
</module>

*/

package org.checkstyle.autofix.recipe.finallocalvariable.annotationdeclaration;

import javax.annotation.Nonnull;

public class OutputAnnotationDeclaration {
    public void basicLocalVariables() {
        @Nonnull final String name = "John Doe";

        @Nonnull
        @Deprecated final String gender = "Male";

        @Nonnull @Deprecated
        final String text = "Hello";
    }
}
