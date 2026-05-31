/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.ArrayTrailingCommaCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.arraytrailingcomma.differentlinearraytrailingcomma;

public class OutputDifferentLineArrayTrailingComma {

    int[] multiElementMultiLine = new int[] {
        1,
        2,
        3,
    };

    int[] singleLineNoComma = new int[] { 1, 2, 3 };

}
