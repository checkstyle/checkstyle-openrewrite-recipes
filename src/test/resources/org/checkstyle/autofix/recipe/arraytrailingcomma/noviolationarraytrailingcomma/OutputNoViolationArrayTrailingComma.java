/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.ArrayTrailingCommaCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.arraytrailingcomma.noviolationarraytrailingcomma;

public class OutputNoViolationArrayTrailingComma {

    int[] alreadyHasComma = new int[] {
        1,
        2,
        3,
    };

    int[] singleLine = new int[] { 1, 2, 3 };

    int[] singleElementSingleLine = new int[] { 42 };

    int[] emptyArray = new int[] {};

    String[] stringsWithComma = new String[] {
        "alpha",
        "beta",
        "gamma",
    };
}
