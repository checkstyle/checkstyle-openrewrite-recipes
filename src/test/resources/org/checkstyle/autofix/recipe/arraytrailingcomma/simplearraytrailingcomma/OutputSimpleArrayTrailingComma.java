/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="com.puppycrawl.tools.checkstyle.checks.coding.ArrayTrailingCommaCheck"/>
  </module>
</module>
*/

package org.checkstyle.autofix.recipe.arraytrailingcomma.simplearraytrailingcomma;

public class OutputSimpleArrayTrailingComma {

    int[] singleElementMultiLine = new int[] {
        1,
    };

    int[] multiElementMultiLine = new int[] {
        1,
        2,
        3,
    };

    String[] stringArray = new String[] {
        "hello",
        "world",
    };

    int[] alreadyHasComma = new int[] {
        1,
        2,
        3,
    };

    int[] singleLine = new int[] { 1, 2, 3 };

    int[] emptyArray = new int[] {};

    int[] sizedArray = new int[5];

    int[][] nestedArray = new int[][] {
        {
            1,
        },
    };
}
